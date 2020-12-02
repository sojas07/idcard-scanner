package com.example.idcard_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class QrDetails extends AppCompatActivity {
    TextView name, rollno, division, yearofstudy, id;
    TableLayout tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_details);
        name = findViewById(R.id.nameTextView);
        rollno = findViewById(R.id.rollnoTextView);
        division = findViewById(R.id.divisionTextView);
        yearofstudy = findViewById(R.id.yearofstudyTextView);
        id = findViewById(R.id.idTextView);
//        tl = findViewById(R.id.tableLayout);
        String jsonString = getIntent().getStringExtra("data");
        try {
            setQrData(new JSONObject(jsonString));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"Oops...There was an error scanning this QR Code",Toast.LENGTH_SHORT).show();
        }
    }

    public void setQrData(JSONObject data) throws JSONException{
        String fullName = data.getString("firstName").toUpperCase() + " " + data.getString("lastName").toUpperCase();
        name.setText(fullName);
        rollno.setText(data.getString("rollNo"));
        id.setText(data.getString("id"));
        yearofstudy.setText(data.getString("yearOfStudy"));
        division.setText(data.getString("division"));
        }

    }

