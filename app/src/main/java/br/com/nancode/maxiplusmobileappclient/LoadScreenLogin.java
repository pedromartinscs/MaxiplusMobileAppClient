package br.com.nancode.maxiplusmobileappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadScreenLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen_login);

        String user = getIntent().getStringExtra("EXTRA_TESTE_LOGIN");
        String pass = getIntent().getStringExtra("EXTRA_TESTE_PASS");
        new LoadScreenASYNC(LoadScreenLogin.this).execute(user, pass);
    }
}