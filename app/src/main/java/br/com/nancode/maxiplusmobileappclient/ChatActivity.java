package br.com.nancode.maxiplusmobileappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ChatActivity extends AppCompatActivity {
    private String user;
    private String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.user = getIntent().getStringExtra("EXTRA_SESSION_LOGIN");
        this.pass = getIntent().getStringExtra("EXTRA_SESSION_PASS");
        /*Thread t = new Thread() {
            String link = "http://www.maxiplusseguros.com.br/MaxiMobileWebServer/atualiza_chat.php";
            String data;
            @Override
            public void run() {
                try {
                    URL url = new URL(link);
                    URLConnection conn;
                    OutputStreamWriter wr;
                    BufferedReader reader;
                    StringBuilder sb;
                    String line;
                    String resultado;
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        try {
                            data = URLEncoder.encode("user", "UTF-8") + "=" +
                                    URLEncoder.encode(user, "UTF-8");
                            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" +
                                    URLEncoder.encode(pass, "UTF-8");

                            conn = url.openConnection();

                            conn.setDoOutput(true);
                            wr = new OutputStreamWriter(conn.getOutputStream());

                            wr.write( data );
                            wr.flush();

                            reader = new BufferedReader(new
                                    InputStreamReader(conn.getInputStream()));

                            sb = new StringBuilder();

                            // Read Server Response
                            while((line = reader.readLine()) != null) {
                                sb.append(line);
                                break;
                            }
                            resultado = sb.toString();
                        } catch(Exception e){
                            //do something about it
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //updateTextView();
                            }
                        });
                    }
                } catch (Exception e){

                }
            }
        }; */

        //t.start();
    }

    public void sendMessage(View view) {
        try {
            EditText mensagem = (EditText)findViewById(R.id.messageToSend);
            ListView listaMensagens = (ListView)findViewById(R.id.messageListView);
            ImageButton send = (ImageButton)findViewById(R.id.sendButton);
            send.setEnabled(false);
            new SendMessageASYNC(ChatActivity.this).execute(this.user, this.pass, mensagem.getText().toString().replace("&", ""));
        }
        catch (Exception e){

        }
    }
}
