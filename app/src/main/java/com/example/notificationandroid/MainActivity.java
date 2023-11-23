package com.example.notificationandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.media.session.MediaButtonReceiver;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import com.example.notificationandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //    private ActivityMainBinding binding;
    private NotificationManagerCompat notificationManagerCompat;
    EditText title, text;

    static List<Message> MESSAGES = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        title = findViewById(R.id.edit_title);
        text = findViewById(R.id.edit_message);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Notification notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.baseline_cake_24)
                        .setColor(Color.GREEN)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setColor(Color.BLUE)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .build();

                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notificationManagerCompat.notify(1, notification);
                    Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                }


            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int progressMax = 100;

                NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.baseline_calculate_24)
                        .setContentTitle("Download")
                        .setContentText("Download in progress")
                        .setOngoing(true)
                        .setOnlyAlertOnce(true)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setProgress(progressMax, 0, false);

                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notificationManagerCompat.notify(2, notification.build());
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        for (int progress = 0; progress <= progressMax; progress += 10) {
                            notification.setProgress(progressMax, progress, false);
                            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                                notificationManagerCompat.notify(2, notification.build());
                                SystemClock.sleep(1000);
                            }
                        }
                    }
                }).start();

                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notification.setContentText("Download finished")
                            .setOngoing(false)
                            .setProgress(0,0,false);
                    notificationManagerCompat.notify(2, notification.build());
                }

            }
        });

    }


}