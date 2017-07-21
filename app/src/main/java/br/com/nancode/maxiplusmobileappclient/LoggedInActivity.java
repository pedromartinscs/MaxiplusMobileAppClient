package br.com.nancode.maxiplusmobileappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.nancode.maxiplusmobileappclient.Model.logModel;
import br.com.nancode.maxiplusmobileappclient.Repository.logRepository;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        logRepository lR = new logRepository(LoggedInActivity.this.getApplicationContext());
        List<logModel> logs = new ArrayList<logModel>();
        logs = lR.SelecionarTodos();
        if(!logs.isEmpty()){
            logModel log = new logModel();
            JSONObject jObject = new JSONObject();
            Integer i;
            String json= "";
            String id = getIntent().getStringExtra("EXTRA_SESSION_ID_EXTERNO");
            String user = getIntent().getStringExtra("EXTRA_SESSION_LOGIN");
            String pass = getIntent().getStringExtra("EXTRA_SESSION_PASS");
            try {
                for (i = 0; i < logs.size(); i++) {
                    log = logs.get(i);
                    jObject.put("logdata" + i, log.getDate());
                    jObject.put("logevento" + i, log.getEvento());
                }
                json = jObject.toString();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            new LogUpdateActivity(LoggedInActivity.this).execute(json, id, user, pass);
        }
    }
}
