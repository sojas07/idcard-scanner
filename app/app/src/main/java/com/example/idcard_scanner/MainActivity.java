package com.example.idcard_scanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Base64;

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
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("data", Context.MODE_PRIVATE);
        File file = new File(directory, "qrcode" + ".png");
        if(file.exists()){
            openQrCodeActivity();
        }
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"Authenticating. Please wait....",Toast.LENGTH_SHORT).show();
                new LoginTask().execute();
            }
        });
    }

    public void openQrCodeActivity(){
        Intent intent = new Intent(this, QrCode.class);
        startActivity(intent);
    }


    private class LoginTask extends AsyncTask<Void, Void, Void> {
        boolean result;

        @RequiresApi(api = Build.VERSION_CODES.O)
        public byte[] decodeImage(String imageDataString) {
            return Base64.getDecoder().decode(imageDataString);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            String username = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            try {
                Socket clientSocket= new Socket ("192.168.0.102", 7777);

                //send username password
                JSONObject obj = new JSONObject();
                obj.put("username", username);
                obj.put("password", password);
                //output stream
                DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));

                outToServer.writeBytes(obj.toString()+ '\n');
                outToServer.flush();
                //read from server
                String message = inFromServer.readLine();

                JSONObject obj1 = (JSONObject) JSONValue.parse(message);
                result = (Boolean) obj1.get("flag");
                String image = obj1.get("image").toString();

                if(result == true) {
                    byte[] imageByteArray = decodeImage(image);
                    //write image
                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File directory = cw.getDir("data", Context.MODE_PRIVATE);
                    File file = new File(directory, "qrcode" + ".png");
//                    File file = new File(MainActivity.this.getFilesDir(), "text");
                    FileOutputStream imageOutFile = new FileOutputStream(file);
                    imageOutFile.write(imageByteArray);
                    imageOutFile.close();}
                else {
                    System.out.println("Invalid!!...");
                }
                System.out.println("Image Written Manipulated!");
                clientSocket.close();

            } catch (FileNotFoundException e) {
                System.out.println("Image not found" + e);
            } catch(SocketTimeoutException e){
                Toast.makeText(getApplicationContext(),"Request timeout",Toast.LENGTH_SHORT).show();
            } catch (SocketException e){
                Toast.makeText(getApplicationContext(),"Connection error",Toast.LENGTH_SHORT).show();
            }
            catch (IOException ioe) {
                System.out.println("Exception while reading the Image " + ioe);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(result){
                Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                openQrCodeActivity();
            }
            else{
                Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_SHORT).show();
            }
        }


    }
}
