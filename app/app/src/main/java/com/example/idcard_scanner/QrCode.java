package com.example.idcard_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.common.BitMatrix;

public class QrCode extends AppCompatActivity {
    ImageView qrCodeImage;
    Button scanButton;
    public static Toast t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        qrCodeImage = findViewById(R.id.imageView);
        scanButton = findViewById(R.id.scanButton);
//        qrCodeImage.setImageURI();
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanQrActivity.class));
            }
        });
    }
}
