package com.example.ravarich.currencyconverterbycamera;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ravarich.currencyconverterbycamera.fragment.GetCurrencyFragment;
import com.example.ravarich.currencyconverterbycamera.fragment.MainFragment;
import com.example.ravarich.currencyconverterbycamera.model.Data;
import com.example.ravarich.currencyconverterbycamera.model.DataDetail;
import com.example.ravarich.currencyconverterbycamera.model.ExchangeRateModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCurrencyActivity extends AppCompatActivity {

    ConnectionDetector cd;
    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_currency);

        getSupportActionBar().hide();


        //สร้างครั้งแรก แปะ fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, GetCurrencyFragment.newInstance())
                    .commit();
        }

        initInstances();
    }

    private void initInstances() {

        //เช็คการเข้า app ครั้งแรก
        useFirstTimes();
        //เช็คการต่อ net
        internetChecking();


    }

    private void useFirstTimes() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (sharedPref.getBoolean("useFirstTimes", true) == true) {
//            Log.e("useFirstTimes", sharedPref.getBoolean("useFirstTimes", true) + "");
            setCurrency();
        } else {
//            Log.e("useFirstTimes", sharedPref.getBoolean("useFirstTimes", true) + "");
        }


    }

    private void setCurrency() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("lastUpdate", "2017-12-08");

        editor.putFloat("USD", (float) 32.4829000);
        editor.putFloat("EUR", (float) 38.3800000);
        editor.putFloat("JPY", (float) 28.6074000);
        editor.putFloat("MYR", (float) 7.8810000);
        editor.putFloat("PHP", (float) 0.6357000);
        editor.putFloat("IDR", (float) 2.3068000);
        editor.putFloat("CHF", (float) 32.9127000);
        editor.putFloat("CNY", (float) 4.8809000);
        editor.putFloat("MMK", (float) 0.0237000);
        editor.putFloat("KRW", (float) 0.0300000);
        editor.putFloat("KHR", (float) 0.0081000);
        editor.putFloat("LAK", (float) 0.0039000);
        editor.putFloat("VND", (float) 0.0014000);
        editor.putFloat("GBP", (float) 43.5182000);

        editor.commit();
    }

    private void internetChecking() {
        cd = new ConnectionDetector(this);

        if (cd.isConnection()) {
            pullData();
        } else {
//            Log.e("internet connection", "Fail");

            // เด้ง alert ถามว่าไป offline อ๊ะเป่า
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(GetCurrencyActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_offline, null);


            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            // ปิดการกดนอก dialo
            dialog.setCanceledOnTouchOutside(false);
            // ปิดการกดปุ่ม back
            dialog.setCancelable(false);
            dialog.show();

            // handle การกดปุ่มบน dialog

            Button btnYes = (Button) mView.findViewById(R.id.btnYes);
            Button btnNo = (Button) mView.findViewById(R.id.btnNo);

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ถ้าใช้ครั้งแรกให้ไป tutorial
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if (sharedPref.getBoolean("useFirstTimes", true) == true) {
//                        Log.e("goTutorial", sharedPref.getBoolean("useFirstTimes", true) + "");
                        Intent intent = new Intent(GetCurrencyActivity.this, TutorialActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
//                        Log.e("goTutorial", sharedPref.getBoolean("useFirstTimes", true) + "");
                        Intent intent = new Intent(GetCurrencyActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    internetChecking();

                    dialog.dismiss();

                }
            });
        }
    }

    private void pullData() {
        Call<ExchangeRateModel> call = HttpManager.getInstance().getService().getExchangeRate(
                Constant.APIKEY,
                "2017-12-12", // startDate
                "2017-12-12"  // endDate
        );
        call.enqueue(new Callback<ExchangeRateModel>() {
            @Override
            public void onResponse(@NonNull Call<ExchangeRateModel> call, @NonNull Response<ExchangeRateModel> response) {
                if (response.isSuccessful()) {
                    // 2oo
                    ExchangeRateModel rawData = response.body();
                    if (rawData != null && rawData.getResult() != null && rawData.getResult().getData() != null) {
                        Data data = rawData.getResult().getData(); //  using this data
                        Gson gson = new Gson();
                        String json = gson.toJson(data);
//                        Log.e("test", data.getDataHeader().getLastUpdated());

                        saveLatestData(data);

                        //ถ้าใช้ครั้งแรกให้ไป tutorial
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        if (sharedPref.getBoolean("useFirstTimes", true) == true) {
//                            Log.e("goTutorial", sharedPref.getBoolean("useFirstTimes", true) + "");
                            Intent intent = new Intent(GetCurrencyActivity.this, TutorialActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
//                            Log.e("goTutorial", sharedPref.getBoolean("useFirstTimes", true) + "");
                            Intent intent = new Intent(GetCurrencyActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // body null
//                    Data data = getLatestCurrency();
                    }
                }
            }


            @Override
            public void onFailure(@NonNull Call<ExchangeRateModel> call, @NonNull Throwable t) {
                // 400+, 500+ error

            }
        });
    }

    private void saveLatestData(Data data) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("lastUpdate", currentDate);

        for (DataDetail currency : data.getDataDetail()) {

            if (currency.getCurrencyId().equals("USD")) {
                editor.putFloat("USD", Float.valueOf(currency.getBuyingTransfer()));
//                Log.e("test", currency.getBuyingTransfer());
            }
            if (currency.getCurrencyId().equals("EUR")) {
                editor.putFloat("EUR", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("JPY")) {
                editor.putFloat("JPY", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("MYR")) {
                editor.putFloat("MYR", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("PHP")) {
                editor.putFloat("PHP", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("IDR")) {
                editor.putFloat("IDR", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("CHF")) {
                editor.putFloat("CHF", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("CNY")) {
                editor.putFloat("CNY", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("MMK")) {
                editor.putFloat("MMK", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("KRW")) {
                editor.putFloat("KRW", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("KHR")) {
                editor.putFloat("KHR", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("LAK")) {
                editor.putFloat("LAK", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("VND")) {
                editor.putFloat("VND", Float.valueOf(currency.getBuyingTransfer()));
            }
            if (currency.getCurrencyId().equals("GBP")) {
                editor.putFloat("GBP", Float.valueOf(currency.getBuyingTransfer()));
            }
            editor.commit();
        }
    }


}
