package com.example.meoryou.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by me or you on 9/28/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // whats going to happen when the alarm goes off
        Toast.makeText(context, "Your alarm is going off!", Toast.LENGTH_SHORT).show();
        callNotification(context,"Ready","Do task","Alert"); // callNotification
    }

    public void callNotification(Context context, String msg, String msgtext, String alert) {
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);
        NotificationCompat.Builder buildNotific = (NotificationCompat.Builder) new NotificationCompat.Builder(context).
                setSmallIcon(R.drawable.n_icon).
                setContentTitle(msg).
                setTicker(alert).
                setContentText(msgtext);

        buildNotific.setContentIntent(notificationIntent);
        buildNotific.setDefaults(NotificationCompat.DEFAULT_SOUND);
        buildNotific.setAutoCancel(true);

        NotificationManager NotMnger = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        NotMnger.notify(1, buildNotific.build());
    }

}
