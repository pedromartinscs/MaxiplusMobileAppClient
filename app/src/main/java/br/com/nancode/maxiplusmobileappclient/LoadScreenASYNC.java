package br.com.nancode.maxiplusmobileappclient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.nancode.maxiplusmobileappclient.Model.logModel;
import br.com.nancode.maxiplusmobileappclient.Model.userModel;
import br.com.nancode.maxiplusmobileappclient.Repository.logRepository;
import br.com.nancode.maxiplusmobileappclient.Repository.userRepository;

/**
 * Created by martins on 15/08/2017.
 */

public class LoadScreenASYNC extends AsyncTask<String, Void, String> {
    private Activity activity;
    public LoadScreenASYNC(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected String doInBackground(String... params) {
        try{
            String username = (String)params[0];
            String password = (String)params[1];
            String type = (String)params[2];
            String link = "";
            if(type.equals("auto")) {
                link = "http://www.maxiplusseguros.com.br/MaxiMobileWebServer/login_existe.php";
            }
            else{
                link = "http://www.maxiplusseguros.com.br/MaxiMobileWebServer/login.php";
            }
            String data  = URLEncoder.encode("user", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if((!s.equals("0")) || (!s.equals(""))){
            try {
                JSONObject jObject = new JSONObject(s);
                userRepository uR = new userRepository(activity.getApplicationContext());
                List<userModel> users = new ArrayList<userModel>();
                userModel user = new userModel();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                users = uR.SelecionarTodos();
                if(!users.isEmpty()) {
                    user = users.get(0);
                    user.setData(df.format(c.getTime()));
                    uR.Atualizar(user);
                }
                else{
                    user.setLogin(jObject.getString("us_login"));
                    user.setSenha(jObject.getString("us_pass"));
                    user.setId(jObject.getInt("us_ID"));
                    user.setData(df.format(c.getTime()));
                    uR.Salvar(user);
                    users = uR.SelecionarTodos();
                    user = users.get(0);
                }


                Intent intent = new Intent(activity, LoggedInActivity.class);
                intent.putExtra("EXTRA_SESSION_LOGIN", user.getLogin());
                intent.putExtra("EXTRA_SESSION_PASS", user.getSenha());
                intent.putExtra("EXTRA_SESSION_ID_INTERNO", user.getID().toString());
                intent.putExtra("EXTRA_SESSION_ID_EXTERNO", user.getId().toString());
                intent.putExtra("EXTRA_SESSION_DATA", user.getData());
                intent.putExtra("EXTRA_SESSION_EMAIL", jObject.getString("us_email"));
                intent.putExtra("EXTRA_SESSION_PONTOS", jObject.getInt("us_pontos"));
                intent.putExtra("EXTRA_SESSION_CPF", jObject.getString("us_cpf"));
                intent.putExtra("EXTRA_SESSION_NOME", jObject.getString("us_nome"));
                activity.startActivity(intent);
                activity.finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            userRepository uR = new userRepository(activity.getApplicationContext());
            uR.LogOut();
            logRepository lR = new logRepository(activity.getApplicationContext());
            logModel log = new logModel();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            log.setEvento("Falha ao tentar fazer login automaticamente");
            log.setDate(df.format(c.getTime()));

            lR.Salvar(log);
            synchronized(this){
                try {
                    this.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
