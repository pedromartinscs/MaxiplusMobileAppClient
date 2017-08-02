package br.com.nancode.maxiplusmobileappclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by martins on 02/08/2017.
 */

public class LoggedInUserFragment extends Fragment {
    private static final String TAG = "UserTabFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_logged_in_user,container,false);
        return view;
    }
}
