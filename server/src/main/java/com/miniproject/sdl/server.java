package com.miniproject.sdl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//import org.json.simple.JSONArray;
//import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class server {
    public static Connection con;

    public static byte[] decodeImage(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }

    public static void connectToDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/idscanner", "root", "aarvi@123");
        } catch (Exception e) {
            System.out.println("Exception in connection: " + e);
        }
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    public static void main(String[] args) throws WriterException, IOException, NotFoundException {
    	connectToDb();
        while (true) {
            
            try {

                // socket connection (PORT 7777)
                ServerSocket welcomeSocket = new ServerSocket(7777);
                System.out.println("server started...");
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient = new BufferedReader(
                        new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                // convert received data
                System.out.println("Credentials Received--->>!");
                String message = inFromClient.readLine();
                JSONObject obj1 = (JSONObject) JSONValue.parse(message);
                String username = obj1.get("username").toString();
                String password = obj1.get("password").toString();
              
                // validate credentials
                db obj = new db();
                obj.con = con;
                JSONObject userobj = obj.checkCredentials(username, password);
                // boolean flag = true;
                if (!userobj.isEmpty()) {
                    genrateQrCode qr = new genrateQrCode();
                    String data = userobj.toJSONString();

                    String path = "output.png";
                    // Encoding charset
                    String charset = "UTF-8";
                    Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
                    hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                    // genrate qr code

                    qr.createQR(data, path, charset, hashMap, 200, 200);

                    // read image genrated
                    String file = "output.png";
                    FileInputStream imageInFile = new FileInputStream(path);
                    byte imageData[] = imageInFile.readAllBytes();
                    System.out.println((imageData.length));

                    // encode image
                    String imageDataString = encodeImage(imageData);
                    System.out.println(imageDataString.length());
                    imageInFile.close();
                    System.out.println("Image Successfully Manipulated!");

                    // response
                    boolean flag1 = true;
                    JSONObject responseObj = new JSONObject();
                    responseObj.put("image", imageDataString);
                    responseObj.put("flag", flag1);
                    // send response

                    outToClient.writeBytes(responseObj.toJSONString());
                    System.out.println("File Sent!");
                    connectionSocket.close();
                } else {
                    JSONObject responseObj = new JSONObject();
                    responseObj.put("image", "empty");
                    boolean flag1 = false;
                    responseObj.put("flag", flag1);
                    // send response

                    outToClient.writeBytes(responseObj.toJSONString());
                    System.out.println("File Sent!");
                    connectionSocket.close();
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } 
        }
    }
}