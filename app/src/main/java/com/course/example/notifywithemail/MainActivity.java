package com.course.example.notifywithemail;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder notifyDetails = null;
    private int SIMPLE_NOTFICATION_ID = 1;
    private String contentTitle = "Email Notification";
    private String contentText = "Get to Email by clicking me";
    private String tickerText = "New Alert - Pull Down Status Bar";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        //As of API 26 Notification Channels must be assigned to a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel foobar",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel description");
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(channel);
        }

        //create implicit intent for action when notification selected
        //from expanded notification screen
        //open email when notification clicked
        Intent notifyIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

        notifyIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jpepe@bentley.edu", "jpepe44@gmail.com"});
        notifyIntent.putExtra(Intent.EXTRA_TEXT, "We are under a Klingon attack. Fired Photon Torpedos.");
        notifyIntent.putExtra(Intent.EXTRA_SUBJECT, "Klingon Bird of Prey Attack");

        //create pending intent to wrap intent so that it
        //will fire when notification selected.
        //The PendingIntent can only be used once.
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent,
                PendingIntent.FLAG_ONE_SHOT);

        //set icon, text, and time on notification status bar
        notifyDetails = new NotificationCompat.Builder(this, "default")
                .setContentIntent(pendingIntent)

                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.droid)
                .setAutoCancel(true)     //cancel Notification after clicking on it
                //set Android to vibrate when notified
                .setVibrate(new long[] {1000, 1000, 2000, 2000})

                //set sound to play
                .setSound(Uri.parse("android.resource://com.course.example.notify/"+R.raw.photon));

        Button start = (Button) findViewById(R.id.btn_showsample);
        Button cancel = (Button) findViewById(R.id.btn_clear);

        start.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                //notify() in response to button click.
                mNotificationManager.notify(SIMPLE_NOTFICATION_ID,
                        notifyDetails.build());

            }
        });

        cancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                mNotificationManager.cancel(SIMPLE_NOTFICATION_ID);
            }


        });
    }
}
