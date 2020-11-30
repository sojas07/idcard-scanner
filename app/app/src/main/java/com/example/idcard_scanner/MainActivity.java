package com.example.idcard_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static EditText userNameEditText;
    public static EditText passwordEditText;
    public static Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                //Send credentials to server for authentication;
                Boolean isAuthenticated = true;
                if(isAuthenticated) {
                    openNewActivity();
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openNewActivity(){
        Intent intent = new Intent(this, QrCode.class);
        startActivity(intent);
    }

}
