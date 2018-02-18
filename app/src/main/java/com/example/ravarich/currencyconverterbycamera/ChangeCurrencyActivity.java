package com.example.ravarich.currencyconverterbycamera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ChangeCurrencyActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnGoMainActivity;
    Spinner spinnerCurrency;
    Button btnWriteCurrency;
    Button btnReadCurrency;
    TextView tvResult1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_currency);

        initInstances();
    }

    private void initInstances() {
        btnGoMainActivity = (Button) findViewById(R.id.goMainActivity);
        btnGoMainActivity.setOnClickListener(this);



        // spiner

        spinnerCurrency = (Spinner) findViewById(R.id.spinnerCurrency);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ChangeCurrencyActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.currencylists));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(myAdapter);


    }



    @Override
    public void onClick(View view) {
        if (view == btnGoMainActivity) {
            //save user_currency
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("user_currency",
                    spinnerCurrency.getSelectedItem().toString());
            editor.commit();
            //จ่าหน้าซอง
            Intent intent = new Intent(ChangeCurrencyActivity.this,
                    MainActivity.class);
            startActivity(intent);
        }
    }
}
