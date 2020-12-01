package com.example.idcard_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.net.URI;

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
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("data", Context.MODE_PRIVATE);
        File file = new File(directory, "qrcode" + ".png");
        qrCodeImage.setImageDrawable(Drawable.createFromPath(file.toString()));
        qrCodeImage.setScaleType(ImageView.ScaleType.FIT_XY);
//        qrCodeImage.setImageURI(URI.create(file.getAbsolutePath()));
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanQrActivity.class));
            }
        });
    }
}
