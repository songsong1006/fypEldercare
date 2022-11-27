package com.example.bottomnavigation.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.bottomnavigation.Activity.AddReminderActivity;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.ReminderBroadcast;
import com.example.bottomnavigation.Data.AlarmReminderContract;

public class ReminderAlarmService extends IntentService {

    private static final String TAG = ReminderAlarmService.class.getSimpleName();

    public static final int NOTIFICATION_ID = 42;

    Cursor cursor;
    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_IMMUTABLE);
    }

    public ReminderAlarmService() {
        super(TAG);
    }

    public int getId(){
        return NOTIFICATION_ID;
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ElderCare";
            String description = "ElderCare";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri uri = intent.getData();

        //Display a notification to view the task details
        Intent action = new Intent(this, AddReminderActivity.class);
        action.setData(uri);
        //PendingIntent operation = TaskStackBuilder.create(this)
         //       .addNextIntentWithParentStack(action)
           //     .getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE);
        Intent notificationServiceIntent  = new Intent(ReminderAlarmService.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderAlarmService.this, 0, notificationServiceIntent, PendingIntent.FLAG_IMMUTABLE);

        //Grab the task description
        if(uri != null){
            cursor = getContentResolver().query(uri, null, null, null, null);
        }

        String description = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {
                description = AlarmReminderContract.getColumnString(cursor, AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.reminder_title))
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);
    }
}
