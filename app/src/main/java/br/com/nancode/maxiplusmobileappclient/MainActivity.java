package br.com.nancode.maxiplusmobileappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signIn(View view) {
        /* Coleta dados de login */
        EditText loginEdit = (EditText) findViewById(R.id.editText);
        EditText senhaEdit = (EditText) findViewById(R.id.editText2);
        String login = loginEdit.getText().toString();
        String senha = senhaEdit.getText().toString();

        TextView status = (TextView) findViewById(R.id.textView);

        /* Valida usuário e senha */
        new SigninActivity(status).execute(login, senha);

        /* Abre activity de usuário logado */
        /*
        Intent intent = new Intent(this, LoggedInActivity.class);
        intent.putExtra("login", login);
        intent.putExtra("senha", senha);
        startActivity(intent); */
    }

}
