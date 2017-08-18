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
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.List;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.widget.TextView;

        import org.json.JSONException;
        import org.json.JSONObject;
        import br.com.nancode.maxiplusmobileappclient.Model.userModel;
        import br.com.nancode.maxiplusmobileappclient.Repository.userRepository;

public class SigninActivity extends AsyncTask<String, Void, String>{
    private TextView statusField;
    private Activity activity;

    public SigninActivity(TextView statusField, Activity activity) {
        this.statusField = statusField;
        this.activity = activity;
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

    protected void onPostExecute(String result) {
        if(result.equals("0")){
            this.statusField.setText(R.string.mainactivity_error_login);
        }
        else {
            this.statusField.setText(R.string.mainactivity_success_login);
            try {
                JSONObject jObject = new JSONObject(result);
                List<userModel> users = new ArrayList<userModel>();
                userModel UserModel = new userModel();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userRepository uR = new userRepository(activity.getApplicationContext());
                Intent intent = new Intent(activity, LoggedInActivity.class);

                UserModel.setLogin(jObject.getString("us_login"));
                UserModel.setSenha(jObject.getString("us_pass"));
                UserModel.setId(jObject.getInt("us_ID"));
                UserModel.setData(df.format(c.getTime()));

                uR.LogOut();
                uR.Salvar(UserModel);
                users = uR.SelecionarTodos();
                if(!users.isEmpty()) {
                    UserModel = users.get(0);
                    intent.putExtra("EXTRA_SESSION_LOGIN", UserModel.getLogin());
                    intent.putExtra("EXTRA_SESSION_PASS", UserModel.getSenha());
                    intent.putExtra("EXTRA_SESSION_ID_INTERNO", UserModel.getID().toString());
                    intent.putExtra("EXTRA_SESSION_ID_EXTERNO", UserModel.getId().toString());
                    intent.putExtra("EXTRA_SESSION_DATA", UserModel.getData());
                    intent.putExtra("EXTRA_SESSION_EMAIL", jObject.getString("us_email"));
                    intent.putExtra("EXTRA_SESSION_PONTOS", jObject.getInt("us_pontos"));
                    intent.putExtra("EXTRA_SESSION_CPF", jObject.getString("us_cpf"));
                    intent.putExtra("EXTRA_SESSION_NOME", jObject.getString("us_nome"));
                    activity.startActivity(intent);
                    activity.finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}