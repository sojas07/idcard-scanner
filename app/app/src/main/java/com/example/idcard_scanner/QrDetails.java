package com.example.idcard_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class QrDetails extends AppCompatActivity {
    TextView name, address, division, department, mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_details);
        name = findViewById(R.id.nameTextView);
        address = findViewById(R.id.addressTextView);
        division = findViewById(R.id.divisionTextView);
        department = findViewById(R.id.departmentTextView);
        mobile = findViewById(R.id.mobileTextView);
        String jsonString = getIntent().getStringExtra("data");
        try {
            setQrData(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setQrData(JSONObject data) throws JSONException{
        String fullName = data.getString("firstName") + " " + data.getString("lastName");
        name.setText(fullName);
        address.setText(data.getString("address"));
        mobile.setText(data.getString("mobileNo"));
        department.setText(data.getString("department"));
        division.setText(data.getString("division"));
    }
}
