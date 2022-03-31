package com.course.example.notifywithemail;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder notifyDetails = null;
    public static final String ANDROID_CHANNEL_ID = "com.chikeandroid.tutsplustalerts.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    public static final int SIMPLE_NOTFICATION_ID = 101;
    private String contentTitle = "Email Notification";
    private String contentText = "Get to Email by clicking me";
    private String tickerText = "New Alert - Pull Down Status Bar";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = (Button) findViewById(R.id.btn_showsample);
        Button cancel = (Button) findViewById(R.id.btn_clear);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        //Notifications must be assigned to a channel
            NotificationChannel channel = new NotificationChannel(ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel description");
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.GREEN);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            mNotificationManager.createNotificationChannel(channel);


        //create implicit intent for action when notification selected
        //from expanded notification screen
        //open email when notification clicked
        Intent notifyIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

        notifyIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jpepe@bentley.edu", "jpepe44@gmail.com"});
        notifyIntent.putExtra(Intent.EXTRA_TEXT, "We are under a Klingon attack. Fired Photon Torpedos.");
        notifyIntent.putExtra(Intent.EXTRA_SUBJECT, "Klingon Bird of Prey Attack");

        //create pending intent to wrap intent so that it will fire when notification selected.
        //The PendingIntent can only be used once.
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent,0);
            //    PendingIntent.FLAG_ONE_SHOT);

        final Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.droid)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        start.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //notify() in response to button click.
                mNotificationManager.notify(SIMPLE_NOTFICATION_ID, nb.build());
            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mNotificationManager.cancel(SIMPLE_NOTFICATION_ID);
            }
        });
    }
}
