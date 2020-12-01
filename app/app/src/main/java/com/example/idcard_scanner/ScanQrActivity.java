package com.example.idcard_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanQrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this); // Programmatically initialize the scanner view
        mScannerView.setHapticFeedbackEnabled(true);
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            Vibrator vibe = (Vibrator) getSystemService( VIBRATOR_SERVICE );
            vibe.vibrate(50);
            JSONObject result = new JSONObject(rawResult.getText());
            Toast.makeText(getApplicationContext(),rawResult.getText(),Toast.LENGTH_SHORT).show();
            openQrDetailsActivity(rawResult.getText());
        }catch(JSONException e){
            Toast.makeText(getApplicationContext(),"Error scanning code",Toast.LENGTH_SHORT).show();
        }

    }

    public void openQrDetailsActivity(String data){
        Intent intent = new Intent(this, QrDetails.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

}