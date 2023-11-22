package com.example.notificationandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.notificationandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
//    private ActivityMainBinding binding;
    private NotificationManagerCompat notificationManagerCompat;
    EditText title,text;

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
                        .setContentTitle(title.getText().toString())
                        .setContentText(text.getText().toString())
                        //It provide same functionality as Notification Important declare in channel but it can be use for lower api level
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notificationManagerCompat.notify(1, notification);
                    Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                }


            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Notification notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.baseline_calculate_24)
                        .setContentTitle(text.getText().toString())
                        .setContentText(title.getText().toString())
                        //It provide same functionality as Notification Important declare in channel but it can be use for lower api level
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build();

                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notificationManagerCompat.notify(2, notification);
                }
            }
        });

    }

}