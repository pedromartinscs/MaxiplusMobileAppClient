package br.com.nancode.maxiplusmobileappclient;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.nancode.maxiplusmobileappclient.CircleImageView.CircleImageView;

/**
 * Created by martins on 02/08/2017.
 */

public class LoggedInUserFragment extends Fragment {
    private static final String TAG = "UserTabFragment";
    private Bundle savedState = null;
    private CircleImageView CIV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_logged_in_user,container,false);
        CIV = (CircleImageView) view.findViewById(R.id.CircleImageViewUserPhoto);
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("UserFragment");
        }
        if(savedState != null) {
            Bitmap bitmap = savedState.getParcelable("FotoPerfil");
            CIV.setImageBitmap(bitmap);
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
