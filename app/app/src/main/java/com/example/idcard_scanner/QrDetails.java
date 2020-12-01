package com.example.idcard_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class QrDetails extends AppCompatActivity {
    TextView name, address, division, department, mobile;
    TableLayout tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_details);
        name = findViewById(R.id.nameTextView);
        address = findViewById(R.id.addressTextView);
        division = findViewById(R.id.divisionTextView);
        department = findViewById(R.id.departmentTextView);
        mobile = findViewById(R.id.mobileTextView);
        tl = findViewById(R.id.tableLayout);
        String jsonString = getIntent().getStringExtra("data");
        try {
            setQrData(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setQrData(JSONObject data) throws JSONException{
        String fullName = data.getString("firstName").toUpperCase() + " " + data.getString("lastName").toUpperCase();
        name.setText(fullName);
        address.setText(data.getString("address"));
        mobile.setText(data.getString("mobileNo"));
        department.setText(data.getString("department"));
        division.setText(data.getString("division"));
//        JSONArray fieldNames = data.names();
//        int totalFields = data.length();
//        TextView[] textArray = new TextView[totalFields];
//        TableRow[] tr_head = new TableRow[totalFields];
//
//        for(int i=0; i<totalFields;i++) {
////Create the tablerows
//            tr_head[i] = new TableRow(this);
//            tr_head[i].setId(i + 1);
//            tr_head[i].setBackgroundColor(Color.GRAY);
//            tr_head[i].setLayoutParams(new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
//
//            // Here create the TextView dynamically
//
//            textArray[i] = new TextView(this);
//            textArray[i].setId(i + 111);
//            textArray[i].setText("productDescription");
//            textArray[i].setTextColor(Color.WHITE);
//            textArray[i].setPadding(5, 5, 5, 5);
//            tr_head[i].addView(textArray[i]);
//            tl.addView(tr_head[i], new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
//
//        }
        }

    }

