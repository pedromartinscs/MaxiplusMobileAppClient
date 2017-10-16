package br.com.nancode.maxiplusmobileappclient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by martins on 27/09/2017.
 */

public class SignUpASYNC extends AsyncTask<String, Void, String> {
    private Activity activity;
    public SignUpASYNC(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            String nome = (String)params[0];
            String email = (String)params[1];
            String cpf = (String)params[2];
            String login = (String)params[3];
            String pass = (String)params[4];
            String link = "http://www.maxiplusseguros.com.br/MaxiMobileWebServer/novo_login.php";
            String data  = URLEncoder.encode("nome", "UTF-8") + "=" +
                    URLEncoder.encode(nome, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("cpf", "UTF-8") + "=" +
                    URLEncoder.encode(cpf, "UTF-8");
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

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("1")) {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
        else {
            Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
        }
    }
}
