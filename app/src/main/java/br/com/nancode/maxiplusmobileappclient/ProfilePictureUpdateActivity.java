package br.com.nancode.maxiplusmobileappclient;

/**
 * Created by martins on 11/08/2017.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.AsyncTask;

import br.com.nancode.maxiplusmobileappclient.Model.logModel;
import br.com.nancode.maxiplusmobileappclient.Repository.logRepository;

public class ProfilePictureUpdateActivity extends AsyncTask<String, Void, String>{
    private Activity activity;

    public ProfilePictureUpdateActivity(Activity activity) {
        this.activity = activity;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... params) {
        try{
            String image = (String)params[0];
            String id = (String)params[1];
            String login = (String)params[2];
            String pass = (String)params[3];

            String link="http://www.maxiplusseguros.com.br/MaxiMobileWebServer/pictureUpdate.php";
            String data  = URLEncoder.encode("image", "UTF-8") + "=" +
                    URLEncoder.encode(image, "UTF-8");
            data += "&" + URLEncoder.encode("id", "UTF-8") + "=" +
                    URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("login", "UTF-8") + "=" +
                    URLEncoder.encode(login, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" +
                    URLEncoder.encode(pass, "UTF-8");

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

    protected void onPostExecute(String result) {
        if(!result.equals("0")){
            logRepository lR = new logRepository(activity.getApplicationContext());
            logModel log = new logModel();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            log.setEvento("Usuário mudou a foto do perfil");
            log.setDate(df.format(c.getTime()));

            lR.Salvar(log);
        }

    }
}