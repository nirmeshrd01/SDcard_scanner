package com.example.nirmesh.sdcard_scanner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity  {

    Button scan, stop;
    ProgressBar progressBar;
    NotificationManager manager;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        dialog = new ProgressDialog(this);
        scan = (Button) findViewById(R.id.scan);
        stop = (Button) findViewById(R.id.stop);



    }

    public void scanSD(View v) {
        triggerNotification();

        showProgress();
        Intent i = new Intent(this, ScanSD.class);
        startActivity(i);

    }

    public void triggerNotification() {
        Intent intent = new Intent(this, ScanSD.class);
        PendingIntent pi = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.k).setTicker("Scanning in progress").setWhen(System.currentTimeMillis()).setContentTitle("Scanning Started").setContentIntent(pi);
        builder.setAutoCancel(true);

        Notification notification = builder.build();


        manager.notify(101, notification);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager.cancel(101);
    }


    public void showProgress()
    {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }
    public void stopScanSD()
    {
        dialog.dismiss();
    }
    }


