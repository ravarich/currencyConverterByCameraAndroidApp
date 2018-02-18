package com.example.ravarich.currencyconverterbycamera.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ravarich.currencyconverterbycamera.R;

import java.text.DecimalFormat;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ViewRateFragment extends Fragment {

    TextView tvLastUpdate;
    TextView tvCurrency;
    TextView tvUSDRate;
    TextView tvEURRate;
    TextView tvJPYRate;
    TextView tvMYRRate;
    TextView tvPHPRate;
    TextView tvIDRRate;
    TextView tvCHFRate;
    TextView tvCNYRate;
    TextView tvMMKRate;
    TextView tvKRWRate;
    TextView tvKHRRate;
    TextView tvLAKRate;
    TextView tvVNDRate;
    TextView tvGBPRate;
    TextView tvTHBRate;
    EditText etVal;
    ImageButton btnRefresh;

    public ViewRateFragment() {
        super();
    }

    public static ViewRateFragment newInstance() {
        ViewRateFragment fragment = new ViewRateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_rate, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvLastUpdate = (TextView) rootView.findViewById(R.id.tvLastUpdate);
        tvCurrency = (TextView) rootView.findViewById(R.id.tvCurrency);
        tvUSDRate = (TextView) rootView.findViewById(R.id.tvUSDRate);
        tvEURRate = (TextView) rootView.findViewById(R.id.tvEURRate);
        tvJPYRate = (TextView) rootView.findViewById(R.id.tvJPYRate);
        tvMYRRate = (TextView) rootView.findViewById(R.id.tvMYRRate);
        tvPHPRate = (TextView) rootView.findViewById(R.id.tvPHPRate);
        tvIDRRate = (TextView) rootView.findViewById(R.id.tvIDRRate);
        tvCHFRate = (TextView) rootView.findViewById(R.id.tvCHFRate);
        tvCNYRate = (TextView) rootView.findViewById(R.id.tvCNYRate);
        tvMMKRate = (TextView) rootView.findViewById(R.id.tvMMKRate);
        tvKHRRate = (TextView) rootView.findViewById(R.id.tvKHRRate);
        tvKRWRate = (TextView) rootView.findViewById(R.id.tvKRWRate);
        tvLAKRate = (TextView) rootView.findViewById(R.id.tvLAKRate);
        tvVNDRate = (TextView) rootView.findViewById(R.id.tvVNDRate);
        tvGBPRate = (TextView) rootView.findViewById(R.id.tvGBPRate);
        tvTHBRate = (TextView) rootView.findViewById(R.id.tvTHBRate);
        etVal = (EditText) rootView.findViewById(R.id.etVal);
        btnRefresh = (ImageButton) rootView.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rate = Integer.parseInt(etVal.getText().toString());
                Log.e("rate", rate+"");
                setRateInViewRate(rate);
            }
        });

        setRateInViewRate(1);
    }

    private void setRateInViewRate(int rate) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currency = sharedPref.getString("user_currency", "not have data");
        tvCurrency.setText(currency + " :");
        tvLastUpdate.setText("Last Updated : " + sharedPref.getString("lastUpdate", "no date"));
        DecimalFormat df = new DecimalFormat("#.#####");


        if (currency.equals("THB")) {
            tvUSDRate.setText(sharedPref.getFloat("USD", 0) * rate + "");
            tvEURRate.setText(sharedPref.getFloat("EUR", 0) * rate + "");
            tvJPYRate.setText(sharedPref.getFloat("JPY", 0) * rate + "");
            tvMYRRate.setText(sharedPref.getFloat("MYR", 0) * rate + "");
            tvPHPRate.setText(sharedPref.getFloat("PHP", 0) * rate + "");
            tvIDRRate.setText(sharedPref.getFloat("IDR", 0) * rate + "");
            tvCHFRate.setText(sharedPref.getFloat("CHF", 0) * rate + "");
            tvCNYRate.setText(sharedPref.getFloat("CNY", 0) * rate + "");
            tvMMKRate.setText(sharedPref.getFloat("MMK", 0) * rate + "");
            tvKRWRate.setText(sharedPref.getFloat("KRW", 0) * rate + "");
            tvKHRRate.setText(sharedPref.getFloat("KHR", 0) * rate + "");
            tvLAKRate.setText(sharedPref.getFloat("LAK", 0) * rate + "");
            tvVNDRate.setText(sharedPref.getFloat("VND", 0) * rate + "");
            tvGBPRate.setText(sharedPref.getFloat("GBP", 0) * rate + "");
            tvTHBRate.setText(rate+"");
        } else {
            tvUSDRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("USD", 0))) + "");
            tvEURRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("EUR", 0))) + "");
            tvJPYRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("JPY", 0))) + "");
            tvMYRRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("MYR", 0))) + "");
            tvPHPRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("PHP", 0))) + "");
            tvIDRRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("IDR", 0))) + "");
            tvCHFRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("CHF", 0))) + "");
            tvCNYRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("CNY", 0))) + "");
            tvMMKRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("MMK", 0))) + "");
            tvKRWRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("KRW", 0))) + "");
            tvKHRRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("KHR", 0))) + "");
            tvLAKRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("LAK", 0))) + "");
            tvVNDRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("VND", 0))) + "");
            tvGBPRate.setText(df.format(sharedPref.getFloat(currency, 0) * rate / (sharedPref.getFloat("GBP", 0))) + "");
            tvTHBRate.setText(sharedPref.getFloat(currency, 0) * rate + "");
        }

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
