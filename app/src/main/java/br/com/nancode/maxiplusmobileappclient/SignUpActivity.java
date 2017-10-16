package br.com.nancode.maxiplusmobileappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupMainWindowDisplayMode();
        setContentView(R.layout.activity_sign_up);
    }

    public void signUp(View view){
        EditText nomeEdit = (EditText) findViewById(R.id.editText3);
        EditText emailEdit = (EditText) findViewById(R.id.editText4);
        EditText cpfEdit = (EditText) findViewById(R.id.editText5);
        EditText loginEdit = (EditText) findViewById(R.id.editText6);
        EditText passEdit = (EditText) findViewById(R.id.editText7);
        EditText passconfEdit = (EditText) findViewById(R.id.editText8);

        String nome = nomeEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String cpf = cpfEdit.getText().toString();
        String login = loginEdit.getText().toString();
        String pass = passEdit.getText().toString();
        String passconf = passconfEdit.getText().toString();
        if(nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || login.isEmpty() || pass.isEmpty() || passconf.isEmpty()) {
            Toast.makeText(SignUpActivity.this, R.string.signupactivity_warning_fields, Toast.LENGTH_LONG).show();
        }
        else if(pass.length() < 8){
            Toast.makeText(SignUpActivity.this, R.string.signupactivity_warning_pass_size, Toast.LENGTH_LONG).show();
        }
        else if(!pass.equals(passconf)){
            Toast.makeText(SignUpActivity.this, R.string.signupactivity_warning_passconf, Toast.LENGTH_LONG).show();
        }
        else{
            new SignUpASYNC(SignUpActivity.this).execute(nome, email, cpf, login, pass);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void setupMainWindowDisplayMode() {
        View decorView = setSystemUiVisilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUiVisilityMode(); // Needed to avoid exiting immersive_sticky when keyboard is displayed
            }
        });
    }

    private View setSystemUiVisilityMode() {
        View decorView = getWindow().getDecorView();
        int options;
        options =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(options);
        return decorView;
    }
}
