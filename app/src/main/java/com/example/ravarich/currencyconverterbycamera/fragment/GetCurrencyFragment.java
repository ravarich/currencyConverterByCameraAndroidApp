package com.example.ravarich.currencyconverterbycamera.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ravarich.currencyconverterbycamera.GetCurrencyActivity;
import com.example.ravarich.currencyconverterbycamera.MainActivity;
import com.example.ravarich.currencyconverterbycamera.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class GetCurrencyFragment extends Fragment {

    Button btnGoMainActivity;
    TextView tvTitle;
    Button btnShowDialog;

    public GetCurrencyFragment() {
        super();
    }

    public static GetCurrencyFragment newInstance() {
        GetCurrencyFragment fragment = new GetCurrencyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //เปลี่ยน layout ให้ตรงกับ fragment

        View rootView = inflater.inflate(R.layout.fragment_get_currency, container, false);
        initInstances(rootView);
        return rootView;


    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
