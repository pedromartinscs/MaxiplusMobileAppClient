package br.com.nancode.maxiplusmobileappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.nancode.maxiplusmobileappclient.Model.logModel;
import br.com.nancode.maxiplusmobileappclient.Repository.logRepository;
import br.com.nancode.maxiplusmobileappclient.Repository.userRepository;

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

    public void logOut(View view) {
        userRepository uR = new userRepository(LoggedInActivity.this.getApplicationContext());
        uR.LogOut();
        logRepository lR = new logRepository(LoggedInActivity.this.getApplicationContext());
        logModel log = new logModel();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.setEvento("Usuário fez logout");
        log.setDate(df.format(c.getTime()));

        lR.Salvar(log);
        LoggedInActivity.this.finish();
    }

    public void tarefa(View view) {
        logRepository lR = new logRepository(LoggedInActivity.this.getApplicationContext());
        logModel log = new logModel();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.setEvento("Usuário pressinou botão de cotações");
        log.setDate(df.format(c.getTime()));

        lR.Salvar(log);
    }
}