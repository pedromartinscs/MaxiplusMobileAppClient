package br.com.nancode.maxiplusmobileappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

        userRepository uR = new userRepository(MainActivity.this.getApplicationContext());
        List<userModel> users = new ArrayList<userModel>();
        userModel user = new userModel();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        users = uR.SelecionarTodos();
        if(!users.isEmpty()) {
            user = users.get(0);
            user.setData(df.format(c.getTime()));
            uR.Atualizar(user);

            Intent intent = new Intent(getBaseContext(), LoggedInActivity.class);
            intent.putExtra("EXTRA_SESSION_LOGIN", user.getLogin());
            intent.putExtra("EXTRA_SESSION_PASS", user.getSenha());
            intent.putExtra("EXTRA_SESSION_ID_INTERNO", user.getID().toString());
            intent.putExtra("EXTRA_SESSION_ID_EXTERNO", user.getId().toString());
            intent.putExtra("EXTRA_SESSION_DATA", user.getData());

            startActivity(intent);
        }
    }

    public void signIn(View view) {
        /* Coleta dados de login */
        EditText loginEdit = (EditText) findViewById(R.id.editText);
        EditText senhaEdit = (EditText) findViewById(R.id.editText2);
        String login = loginEdit.getText().toString();
        String senha = senhaEdit.getText().toString();

        TextView status = (TextView) findViewById(R.id.textView);

        /* Valida usuário e senha */
        new SigninActivity(status, MainActivity.this).execute(login, senha);
    }

}
