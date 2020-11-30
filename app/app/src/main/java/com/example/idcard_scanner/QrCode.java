package com.example.idcard_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class QrCode extends AppCompatActivity {
    ImageView qrCodeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        qrCodeImage = findViewById(R.id.imageView);
//        qrCodeImage.setImageURI();
    }
}
