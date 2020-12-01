package com.example.idcard_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText userNameEditText;
    EditText passwordEditText;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                //Send credentials to server for authentication;
                boolean isAuthenticated = false;
//                if(isAuthenticated) {
//                try {
//                    isAuthenticated = authenticateUser();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                new MyTask().execute();
//                if(isAuthenticated == true) {
                    openQrCodeActivity();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    public void openQrCodeActivity(){
        Intent intent = new Intent(this, QrCode.class);
        startActivity(intent);
    }


    private class MyTask extends AsyncTask<Void, Void, Void> {
        boolean result;
        @Override
        protected Void doInBackground(Void... voids) {
            String username = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            JSONObject credentials = new JSONObject();
            try {
                credentials.put("username", username);
                credentials.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String serverAddress = "192.168.0.101";
            int port=7777;
            Socket client = null;
            try {
                client = new Socket(serverAddress, port);
                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out=new DataOutputStream(client.getOutputStream());
                out.writeBytes(credentials.toString());
                String jsonString = in.readUTF();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//
//            DataInputStream in=new DataInputStream(client.getInputStream());
//            System.out.println("Message from Server: "+in.readUTF());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
//    public boolean authenticateUser() throws IOException {
//        String username = userNameEditText.getText().toString();
//        String password = passwordEditText.getText().toString();
//        JSONObject credentials = new JSONObject();
//        try {
//            credentials.put("username", username);
//            credentials.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String serverAddress = "192.168.0.101";
//        int port=7777;
//        Socket client = new Socket(serverAddress, port);
//        DataOutputStream out=new DataOutputStream(client.getOutputStream());
//        out.writeBytes(credentials.toString());
//
//        DataInputStream in=new DataInputStream(client.getInputStream());
//        System.out.println("Message from Server: "+in.readUTF());
//        client.close();
//        return true;
//    }