package com.example.notificationandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toast");
//        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        Log.d("SOVAN", "onReceive: "+message);
    }
}
