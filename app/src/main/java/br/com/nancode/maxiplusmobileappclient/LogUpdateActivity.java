package br.com.nancode.maxiplusmobileappclient;

/**
 * Created by martins on 21/07/2017.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.AsyncTask;

import br.com.nancode.maxiplusmobileappclient.Repository.logRepository;

public class LogUpdateActivity extends AsyncTask<String, Void, String>{
    private Activity activity;

    public LogUpdateActivity(Activity activity) {
        this.activity = activity;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... params) {
        try{
            String json = (String)params[0];
            String id = (String)params[1];
            String login = (String)params[2];
            String pass = (String)params[3];

            String link="http://www.maxiplusseguros.com.br/MaxiMobileWebServer/logUpdate.php";
            String data  = URLEncoder.encode("json", "UTF-8") + "=" +
                    URLEncoder.encode(json, "UTF-8");
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
            lR.DeletarTodos();
        }

    }
}