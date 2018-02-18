package com.example.ravarich.currencyconverterbycamera.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ravarich.currencyconverterbycamera.ChangeCurrencyActivity;
import com.example.ravarich.currencyconverterbycamera.MainActivity;
import com.example.ravarich.currencyconverterbycamera.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class Tutorial5Fragment extends Fragment {

    Button btnStart;

    public Tutorial5Fragment() {
        super();
    }

    public static Tutorial5Fragment newInstance() {
        Tutorial5Fragment fragment = new Tutorial5Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //เปลี่ยน layout ให้ตรงกับ fragment

        View rootView = inflater.inflate(R.layout.fragment_tutorial5, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(final View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnStart = (Button) rootView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

                if (sharedPref.getBoolean("useFirstTimes", true) == true){
                    Log.e("inTutorial", sharedPref.getBoolean("useFirstTimes",true)+"");
                    Intent intent = new Intent(getActivity(), ChangeCurrencyActivity.class);
                    startActivity(intent);

                }else {
                    Log.e("inTutorial", sharedPref.getBoolean("useFirstTimes",true)+"");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
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
