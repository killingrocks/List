package com.example.meoryou.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.meoryou.myapplication.DataBase.CRUD;
import com.example.meoryou.myapplication.DataBase.Item;

/**
 * Created by me or you on 9/30/2017.
 */

public class ConnectionReceiver extends BroadcastReceiver {
    // im suppose to update the item list when connection is disconnected
    // put a crud object here
    // i need the possition of the item
    //  which would make no sense if i am on the main page
    // the only page i should update is To-do
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isConnected(context)){
            Toast.makeText(context, "Connected ", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, " Not Connected ", Toast.LENGTH_LONG).show();
        }
    }

    // check the connection
    public  boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
