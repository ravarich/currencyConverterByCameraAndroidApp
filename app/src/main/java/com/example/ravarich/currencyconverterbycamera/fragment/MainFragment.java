package com.example.ravarich.currencyconverterbycamera.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ravarich.currencyconverterbycamera.BuildConfig;
import com.example.ravarich.currencyconverterbycamera.R;
import com.example.ravarich.currencyconverterbycamera.model.Result;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileDescriptor;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.theartofdev.edmodo.cropper.CropImage;
import com.wordpress.priyankvex.easyocrscannerdemo.Config;
import com.wordpress.priyankvex.easyocrscannerdemo.EasyOcrScanner;
import com.wordpress.priyankvex.easyocrscannerdemo.EasyOcrScannerListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment implements EasyOcrScannerListener {
    Button btnCamera;
    final int REQUEST_IMAGE_CAPTURE = 1;
    File output = null;

    String datapath = "";
    TessBaseAPI mTess;
    EasyOcrScanner mEasyOcrScanner;
    Bitmap bitmap;

    TextView tvInput;
    TextView tvInputVal;
    TextView tvOutput;
    TextView tvOutputVal;

    public MainFragment() {
        super();
    }

    int someVar;


    public static MainFragment newInstance(int someVar) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        //เก็บค่า
        args.putInt("someVar", someVar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //อ่าน args
        someVar = getArguments().getInt("someVar");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initInstances(rootView);

        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvInput = (TextView) rootView.findViewById(R.id.tvInput);
        tvInputVal = (TextView) rootView.findViewById(R.id.tvInputVal);
        tvOutput = (TextView) rootView.findViewById(R.id.tvOutput);
        tvOutputVal = (TextView) rootView.findViewById(R.id.tvOutputVal);
        mEasyOcrScanner = new EasyOcrScanner(getActivity(), "EasyOcrScanner",
                Config.REQUEST_CODE_CAPTURE_IMAGE, "eng");

        mEasyOcrScanner.setOcrScannerListener(this);

        dispatchTakePictureIntent();
        btnCamera = (Button) rootView.findViewById(R.id.btnCamera);
        //btnCamera.setEnabled(false);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        tvInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvInput.getText().toString().equals("USD")) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                    View mView = getLayoutInflater().inflate(R.layout.dialog_change, null);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    Button btnAUD = (Button) mView.findViewById(R.id.btnAUD);
                    Button btnSGD = (Button) mView.findViewById(R.id.btnSGD);
                    Button btnHKD = (Button) mView.findViewById(R.id.btnHKD);
                    final Float inputVal = Float.valueOf(tvOutputVal.getText().toString());
                    final DecimalFormat df = new DecimalFormat("#.###");
                    btnAUD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvInput.setText("AUD");
                            tvOutputVal.setText(df.format(inputVal / 1.32) + "");
                            dialog.dismiss();
                        }
                    });
                    btnSGD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvInput.setText("SGD");
                            tvOutputVal.setText(df.format(inputVal / 1.35) + "");
                            dialog.dismiss();
                        }
                    });
                    btnHKD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvInput.setText("HKD");
                            tvOutputVal.setText(df.format(inputVal / 7.8) + "");
                            dialog.dismiss();
                        }
                    });
                }

            }
        });

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        output = new File(dir, "PocBotApi" + timeStamp + ".jpeg");

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                FileProvider.getUriForFile(getActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        output));

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {

            Uri imageUri = Uri.fromFile(output);

            CropImage.activity(imageUri)
                    .start(getContext(), this);

//            String dnt = output.getAbsolutePath();
//            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//            bitmap = BitmapFactory.decodeFile(dnt, bmOptions);
//            ivPicture.setImageBitmap(bitmap);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                Uri resultUri = result.getUri();

                Bitmap bitmap12 = null;

                try {
                    ParcelFileDescriptor parcelFileDescriptor =
                            getActivity().getContentResolver().openFileDescriptor(resultUri, "r");
                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                    bitmap12 = BitmapFactory.decodeFileDescriptor(fileDescriptor);

                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                TextRecognizer textRecognizer = new TextRecognizer.Builder(getContext()).build();
                if (!textRecognizer.isOperational())
//                    Log.e("ERROR", "oh my god");
                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap12).build();
                    SparseArray<TextBlock> items = textRecognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }
//                    Log.e("testOCR", "result = " + stringBuilder.toString());
                    convertCurrency(stringBuilder.toString());
                }

//                String dnt = output.getAbsolutePath();
//                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//                bitmap = BitmapFactory.decodeFile(dnt, bmOptions);
//                ivPicture.setImageBitmap(bitmap);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
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

    public void setButtonCamera() {
//        Log.e("test", "setCamera");
        btnCamera.setEnabled(true);
    }

    @Override
    public void onOcrScanStarted(String filePath) {

    }

    @Override
    public void onOcrScanFinished(Bitmap bitmap, String recognizedText) {
//        Log.e("test", recognizedText);
        convertCurrency(recognizedText);
    }

    private void convertCurrency(String resultText) {

        String num = "";
        String letterInput = "";
        String inputCurrency = "no Input";
        for (int i = 0; i < resultText.length(); i++) {
            Character c = resultText.charAt(i);
            if (Character.isDigit(c) || c.toString().equals(".")) {
                num += c;
            } else if (Character.isLetter(c)) {
                letterInput = letterInput + c;
            }
        }
        letterInput = letterInput.toLowerCase();

        if (resultText.toString().contains("THB")) {
            inputCurrency = "THB";
        } else if (resultText.toString().contains("USD") || resultText.toString().contains("$") || letterInput.toString().equals("s")) {
            inputCurrency = "USD";
        } else if (resultText.toString().contains("EUR") || resultText.toString().contains("€") || letterInput.toString().equals("e")) {
            inputCurrency = "EUR";
        } else if (resultText.toString().contains("JPY") || resultText.toString().contains("¥") || letterInput.toString().equals("y")) {
            inputCurrency = "JPY";
        } else if (resultText.toString().contains("MYR")) {
            inputCurrency = "MYR";
        } else if (resultText.toString().contains("PHP")) {
            inputCurrency = "PHP";
        } else if (resultText.toString().contains("IDR")) {
            inputCurrency = "IDR";
        } else if (resultText.toString().contains("CHF")) {
            inputCurrency = "CHF";
        } else if (resultText.toString().contains("CNY")) {
            inputCurrency = "CNY";
        } else if (resultText.toString().contains("MMK")) {
            inputCurrency = "MMK";
        } else if (resultText.toString().contains("KRW")) {
            inputCurrency = "KRW";
        } else if (resultText.toString().contains("KHR")) {
            inputCurrency = "KHR";
        } else if (resultText.toString().contains("LAK")) {
            inputCurrency = "LAK";
        } else if (resultText.toString().contains("VND")) {
            inputCurrency = "VND";
        } else if (resultText.toString().contains("GBP") || resultText.toString().contains("£") || letterInput.toString().equals("f")) {
            inputCurrency = "GBP";
        }

//        if (strCurrecny.matches("(.*)$(.*)")) {
//            inputCurrency = "USD";
//        } else if (strCurrecny.matches("(.*)¥(.*)")) {
//            inputCurrency = "JPY";
//        } else if (strCurrecny.matches("(.*)€(.*)")) {
//            inputCurrency = "EUR";
//        } else if (strCurrecny.matches("(.*)£(.*)")) {
//            inputCurrency = "GBP";
//        } else if (strCurrecny.matches("(.*)THB(.*)")) {
//            inputCurrency = "THB";
//        } else if (strCurrecny.matches("(.*)USD(.*)")) {
//            inputCurrency = "USD";
//        } else if (strCurrecny.matches("(.*)EUR(.*)")) {
//            inputCurrency = "EUR";
//        } else if (strCurrecny.matches("(.*)JPY(.*)")) {
//            inputCurrency = "JPY";
//        } else if (strCurrecny.matches("(.*)MYR(.*)")) {
//            inputCurrency = "MYR";
//        } else if (strCurrecny.matches("(.*)PHP(.*)")) {
//            inputCurrency = "PHP";
//        } else if (strCurrecny.matches("(.*)IDR(.*)")) {
//            inputCurrency = "IDR";
//        } else if (strCurrecny.matches("(.*)CHF(.*)")) {
//            inputCurrency = "CHF";
//        } else if (strCurrecny.matches("(.*)CNY(.*)")) {
//            inputCurrency = "CNY";
//        } else if (strCurrecny.matches("(.*)MMK(.*)")) {
//            inputCurrency = "MMK";
//        } else if (strCurrecny.matches("(.*)KRW(.*)")) {
//            inputCurrency = "KRW";
//        } else if (strCurrecny.matches("(.*)KHR(.*)")) {
//            inputCurrency = "KHR";
//        } else if (strCurrecny.matches("(.*)LAK(.*)")) {
//            inputCurrency = "LAK";
//        } else if (strCurrecny.matches("(.*)VND(.*)")) {
//            inputCurrency = "VND";
//        } else if (strCurrecny.matches("(.*)GBP(.*)")) {
//            inputCurrency = "GBP";
//        }

//        Log.e("num", num);
//        Log.e("inputCurrency", inputCurrency);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currency = sharedPref.getString("user_currency", "no Currency");
//        Log.e("currency", currency);

        float resultValue = 0;
        float inputValue = 0;
        if (num.equals("") || inputCurrency.equals("no Input")) {
            Toast.makeText(getActivity(),
                    "Capture Failed!!",
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            if (currency.equals("THB")) {
                inputValue = sharedPref.getFloat(inputCurrency, 0);
                resultValue = Float.valueOf(num) * inputValue;
            } else {
                inputValue = sharedPref.getFloat(inputCurrency, 0) / sharedPref.getFloat(currency, 0);
                resultValue = Float.valueOf(num) * inputValue;
            }

            tvInput.setText(inputCurrency);
            tvInputVal.setText(num);
            tvOutput.setText(currency);
            tvOutputVal.setText(resultValue + "");
        }
    }

}

