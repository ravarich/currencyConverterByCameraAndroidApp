package com.example.ravarich.currencyconverterbycamera;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;




import com.example.ravarich.currencyconverterbycamera.fragment.AboutFragment;
import com.example.ravarich.currencyconverterbycamera.fragment.MainFragment;
import com.example.ravarich.currencyconverterbycamera.fragment.ViewRateFragment;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ขอ permission
        //requestStoragePermission();
        //สร้างครั้งแรก แปะ fragment
        if (savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainer, MainFragment.newInstance(123),
                                "MainFragment")
                        .commit();
        }



        initInstances();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("MainFragment");
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    private void initInstances() {

        //เช็คการใช้งานครั้งแรก เพื่อนเปลี่ยนค่า
        useFirstTimes();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        Log.e("last update", sharedPref.getString("lastUpdate", "wtf"));
    }
    private void useFirstTimes() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (sharedPref.getBoolean("useFirstTimes", true) == true){

            //เปลี่ยน useFirstTimes เป็น false;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("useFirstTimes", false);
            editor.commit();
//            Log.e("inMain", "save useFirstTime");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestCameraPermission();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    requestStoragePermission(); // request until granted.
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    private void requestStoragePermission() {
//        Log.e("test", "requestCamerapermission");
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {


        }
    }

    private void requestCameraPermission() {
//        Log.e("test", "requestCameraPermission");
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        101);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {


        }
    }














    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





    // options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_change_currency){

            //จ่าหน้าซอง
            Intent intent = new Intent(MainActivity.this,
                    ChangeCurrencyActivity.class);
            startActivity(intent);

            return true;
        }

        if (item.getItemId() == R.id.action_view_rate){

            Fragment fragment = getSupportFragmentManager()
                    .findFragmentById(R.id.contentContainer);

            if (fragment instanceof ViewRateFragment == false){
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right, R.anim.to_lelf,
                                R.anim.from_left, R.anim.to_right)
                        .replace(R.id.contentContainer,
                                ViewRateFragment.newInstance())
                        .addToBackStack(null)

                        .commit();

            }

            return true;
        }

        if (item.getItemId() == R.id.action_tutorial){

            //จ่าหน้าซอง
            Intent intent = new Intent(MainActivity.this,
                    TutorialActivity.class);
            startActivity(intent);

            return true;
        }

        if (item.getItemId() == R.id.action_about){

            Fragment fragment = getSupportFragmentManager()
                    .findFragmentById(R.id.contentContainer);

            if (fragment instanceof AboutFragment == false){
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right, R.anim.to_lelf,
                                R.anim.from_left, R.anim.to_right)
                        .replace(R.id.contentContainer,
                                AboutFragment.newInstance())
                        .addToBackStack(null)

                        .commit();

            }


            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
