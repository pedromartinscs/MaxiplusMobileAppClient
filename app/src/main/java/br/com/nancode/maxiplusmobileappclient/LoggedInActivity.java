package br.com.nancode.maxiplusmobileappclient;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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

    private static final String TAG = "MainTabFragment";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mviewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mviewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mviewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
        tabLayout.bringToFront();


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

    public void AbrirChat(View view) {
        logRepository lR = new logRepository(LoggedInActivity.this.getApplicationContext());
        logModel log = new logModel();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.setEvento("Usuário pressinou botão para iniciar chat");
        log.setDate(df.format(c.getTime()));

        lR.Salvar(log);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoggedInUserFragment(), getResources().getString(R.string.loggedinactivity_user_tab_title));
        adapter.addFragment(new LoggedInCarFragment(), getResources().getString(R.string.loggedinactivity_car_tab_title));
        adapter.addFragment(new LoggedInServicesFragment(), getResources().getString(R.string.loggedinactivity_services_tab_title));

        viewPager.setAdapter(adapter);
    }
}
