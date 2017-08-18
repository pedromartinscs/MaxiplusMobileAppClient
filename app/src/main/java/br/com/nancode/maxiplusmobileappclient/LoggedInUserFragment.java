package br.com.nancode.maxiplusmobileappclient;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.com.nancode.maxiplusmobileappclient.CircleImageView.CircleImageView;

/**
 * Created by martins on 02/08/2017.
 */

public class LoggedInUserFragment extends Fragment {
    private static final String TAG = "UserTabFragment";
    private Bundle savedState = null;
    private CircleImageView CIV;
    private TextView nome;
    private TextView login;
    private TextView cpf;
    private TextView email;
    private TextView pontos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_logged_in_user,container,false);
        CIV = (CircleImageView) view.findViewById(R.id.CircleImageViewUserPhoto);
        nome = (TextView) view.findViewById(R.id.textViewNome);
        login = (TextView) view.findViewById(R.id.textViewLogin);
        cpf = (TextView) view.findViewById(R.id.textViewCPF);
        email = (TextView) view.findViewById(R.id.textViewEmail);
        pontos = (TextView) view.findViewById(R.id.textViewPontos);
        if(savedInstanceState == null && savedState == null){
            //carrega imagem pela primeira vez
            ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
            File directory = cw.getDir("IMAGES", Context.MODE_PRIVATE);
            File mypath=new File(directory,"FOTO_PERFIL");
            if(mypath.exists()) {
                try {
                    CIV.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(mypath)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            //carrega dados pela primeira vez
            nome.setText(getActivity().getIntent().getStringExtra("EXTRA_SESSION_NOME"));
            login.setText(getActivity().getIntent().getStringExtra("EXTRA_SESSION_LOGIN"));
            email.setText(getActivity().getIntent().getStringExtra("EXTRA_SESSION_EMAIL"));
            cpf.setText(getActivity().getIntent().getStringExtra("EXTRA_SESSION_CPF"));
            pontos.setText(String.valueOf(getActivity().getIntent().getIntExtra("EXTRA_SESSION_PONTOS", 0)));
        }
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("UserFragment");
        }
        if(savedState != null) {
            Bitmap bitmap = savedState.getParcelable("FotoPerfil");
            CIV.setImageBitmap(bitmap);
            nome.setText(savedState.getString("nome"));
            login.setText(savedState.getString("login"));
            email.setText(savedState.getString("email"));
            cpf.setText(savedState.getString("cpf"));
            pontos.setText(savedState.getString("pontos"));
        }
        savedState = null;

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedState = saveState();
    }

    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        state.putParcelable("FotoPerfil", ((BitmapDrawable)CIV.getDrawable()).getBitmap());
        state.putString("nome", nome.getText().toString());
        state.putString("email", email.getText().toString());
        state.putString("cpf", cpf.getText().toString());
        state.putString("login", login.getText().toString());
        state.putString("pontos", pontos.getText().toString());
        return state;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("UserFragment");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle savedStateNow = null;
        if(savedState == null){
            savedStateNow = saveState();
        }
        else{
            savedStateNow = savedState;
        }
        outState.putBundle("UserFragment",savedStateNow);
    }
}
