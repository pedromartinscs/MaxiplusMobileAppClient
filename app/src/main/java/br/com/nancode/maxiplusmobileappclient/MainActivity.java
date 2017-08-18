package br.com.nancode.maxiplusmobileappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

import br.com.nancode.maxiplusmobileappclient.Model.userModel;
import br.com.nancode.maxiplusmobileappclient.Repository.userRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        userRepository uR = new userRepository(MainActivity.this.getApplicationContext());
        List<userModel> users = new ArrayList<userModel>();
        userModel user = new userModel();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        users = uR.SelecionarTodos();
        if(!users.isEmpty()) {
            user = users.get(0);
            Intent intent = new Intent(this, LoadScreenLogin.class);
            intent.putExtra("EXTRA_TESTE_LOGIN", user.getLogin());
            intent.putExtra("EXTRA_TESTE_PASS", user.getSenha());
            intent.putExtra("EXTRA_TESTE_TIPO", "auto");
            startActivity(intent);
            finish();
        }
    }

    public void signIn(View view) {
        /* Coleta dados de login */
        EditText loginEdit = (EditText) findViewById(R.id.editText);
        EditText senhaEdit = (EditText) findViewById(R.id.editText2);
        String login = loginEdit.getText().toString();
        String senha = senhaEdit.getText().toString();

        Intent intent = new Intent(this, LoadScreenLogin.class);
        intent.putExtra("EXTRA_TESTE_LOGIN", login);
        intent.putExtra("EXTRA_TESTE_PASS", senha);
        intent.putExtra("EXTRA_TESTE_TIPO", "manual");
        startActivity(intent);
        finish();
    }

}
