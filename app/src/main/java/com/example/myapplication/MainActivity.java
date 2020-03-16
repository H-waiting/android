package com.example.myapplication;

import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    private ConnectFTP ConnectFTP;
    String currentPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectFTP = new ConnectFTP();;

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean status = false;
                status = ConnectFTP.ftpConnect("112.175.184.80", "dksqud312", "rnfma1227!", 21);
                if(status == true) {
                    Log.d(TAG, "Connection Success");
                }
                else {
                    Log.d(TAG, "Connection failed");
                }


            }
        }).start();
        new Thread(new Runnable() {
            public void run() {

                String currentPath = ConnectFTP.ftpGetDirectory();

            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                boolean status = false;
                String newFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FTPTest";
                File file = new File(newFilePath);
                file.mkdirs();
                newFilePath += "bg3.png";
                try {
                    file = new File(newFilePath);
                    file.createNewFile();
                }catch (Exception e){}

                ConnectFTP.ftpDownloadFile(currentPath + "bg3.png", newFilePath);
                if(status == true) {
                    Log.d(TAG, "Connection Success");
                }
                else {
                    Log.d(TAG, "Connection failed");
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                boolean result = ConnectFTP.ftpDisconnect();
                if(result == true)
                    Log.d(TAG, "DisConnection Success");
                else
                    Log.d(TAG, "DisConnection Success");
            }
        }).start();
    }}
