package br.com.nancode.maxiplusmobileappclient;

/**
 * Created by martins on 19/07/2017.
 * Realiza login
 */
        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.net.URL;
        import java.net.URLConnection;
        import java.net.URLEncoder;


        import android.os.AsyncTask;
        import android.widget.TextView;

public class SigninActivity extends AsyncTask<String, Void, String>{
    private TextView statusField;

    public SigninActivity(TextView statusField) {
        this.statusField = statusField;
    }

    protected void onPreExecute(){
        this.statusField.setText(R.string.mainactivity_try_login);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            String username = (String)params[0];
            String password = (String)params[1];

            String link="http://www.maxiplusseguros.com.br/MaxiMobileWebServer/login.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
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

    protected void onPostExecute(String result) {
        if(result.equals("0")){
            this.statusField.setText(R.string.mainactivity_error_login);
        }
        else {
            this.statusField.setText(R.string.mainactivity_success_login);
        }


    }
}