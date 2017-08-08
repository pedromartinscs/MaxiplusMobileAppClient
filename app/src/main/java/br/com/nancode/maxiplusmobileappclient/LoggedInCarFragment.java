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

public class LoggedInCarFragment extends Fragment {
    private static final String TAG = "CarTabFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_logged_in_car,container,false);
        return view;
    }
}
