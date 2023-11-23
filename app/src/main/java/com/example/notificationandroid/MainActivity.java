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
    EditText title,text;

    static List<Message> MESSAGES = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        title = findViewById(R.id.edit_title);
        text = findViewById(R.id.edit_message);

        MESSAGES.add(new Message("GOOD MORNING","biswa"));
        MESSAGES.add(new Message("Same",null));
        MESSAGES.add(new Message("hru??","biswa"));
        MESSAGES.add(new Message("Fine",null));
        MESSAGES.add(new Message("Good to know","biswa"));
        MESSAGES.add(new Message("Bye",null));


        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activityIntent = new Intent(MainActivity.this,MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,0,activityIntent, PendingIntent.FLAG_IMMUTABLE);

                RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
                        .setLabel("Your answer...")
                        .build();

                Intent replayIntent = new Intent(MainActivity.this, DirectReplayReceiver.class);
                PendingIntent replayPendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,replayIntent, PendingIntent.FLAG_IMMUTABLE);

                NotificationCompat.Action replayAction = new NotificationCompat.Action.Builder(
                        R.drawable.baseline_send_24,
                        "Replay",
                        replayPendingIntent
                ).addRemoteInput(remoteInput).build();

                NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle("Me");
                messagingStyle.setConversationTitle("Group Chat");

                for (Message chatMessage: MESSAGES){
                    NotificationCompat.MessagingStyle.Message notificationMessage =
                            new NotificationCompat.MessagingStyle.Message(
                                    chatMessage.getText(),
                                    chatMessage.getTimestamp(),
                                    chatMessage.getSender()
                            );
                    messagingStyle.addMessage(notificationMessage);
                }

                Notification notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.baseline_cake_24)
                        .setStyle(messagingStyle)
                        .addAction(replayAction)
                        .setColor(Color.GREEN)
                        //It provide same functionality as Notification Important declare in channel but it can be use for lower api level
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setColor(Color.BLUE)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
//                        .addAction(R.drawable.baseline_cake_24,"Toast",actionIntent)
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
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.sample);
                Bitmap artwork = BitmapFactory.decodeResource(getResources(),R.drawable.sample2);

                MediaMetadataCompat mediaMetadata; // It used to set Metadata of Media on notification like Title or Artist
                MediaSessionCompat mediaSession; // It used to connect our notification to Media player library

                mediaSession = new MediaSessionCompat(getApplicationContext(), "tag");
                mediaSession.setActive(true);
                // Set the media session metadata
                mediaMetadata = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "Song Title")
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "Artist Name")
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART,BitmapFactory.decodeResource(getResources(), R.drawable.sample))
                        .build();
                mediaSession.setMetadata(mediaMetadata);

                Notification notification = new NotificationCompat.Builder(MainActivity.this, App.CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.baseline_calculate_24)
                        .setContentTitle(text.getText().toString())
                        .setContentText(title.getText().toString())
                        .setLargeIcon(artwork)
                        .addAction(R.drawable.baseline_skip_previous_24,"Previous",null)
                        .addAction(R.drawable.baseline_play_arrow_24,"play", MediaButtonReceiver.buildMediaButtonPendingIntent(
                                MainActivity.this,
                                PlaybackStateCompat.ACTION_PLAY_PAUSE
                        ))
                        .addAction(R.drawable.baseline_skip_next_24,"next",null)
                        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                .setMediaSession(mediaSession.getSessionToken())
                                .setShowActionsInCompactView(0, 1, 2)
                        )
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