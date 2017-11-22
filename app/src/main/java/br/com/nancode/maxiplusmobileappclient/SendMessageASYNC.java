package br.com.nancode.maxiplusmobileappclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by martins on 03/11/2017.
 */

public class SendMessageASYNC  extends AsyncTask<String, Void, String> {
    private Activity activity;
    public SendMessageASYNC(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params){
        try {
            String user = (String) params[0];
            String pass = (String) params[1];
            String msg = (String) params[2];
            String link = "http://www.maxiplusseguros.com.br/MaxiMobileWebServer/envia_mensagem.php";
            String data = URLEncoder.encode("user", "UTF-8") + "=" +
                    URLEncoder.encode(user, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" +
                    URLEncoder.encode(pass, "UTF-8");
            data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" +
                    URLEncoder.encode(msg, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        }
        catch (Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("1")){
            //MENSAGEM ENVIADA
            EditText mensagem = (EditText)activity.findViewById(R.id.messageToSend);
            ImageButton send = (ImageButton)activity.findViewById(R.id.sendButton);
            mensagem.setText("");
            send.setEnabled(true);
        }
        else{
            ImageButton send = (ImageButton)activity.findViewById(R.id.sendButton);
            Toast.makeText(activity,R.string.chatactivity_not_sent,Toast.LENGTH_LONG).show();
            send.setEnabled(true);
        }
    }
}
