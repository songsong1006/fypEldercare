package com.example.bottomnavigation.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.bottomnavigation.Data.DatabaseHelper;
import com.example.bottomnavigation.Model.Alarm;

import java.util.ArrayList;
import java.util.List;

public class LoadAlarmsService extends IntentService {

    private static final String TAG = LoadAlarmsService.class.getSimpleName();
    public static final String ACTION_COMPLETE = TAG + ".ACTION_COMPLETE";
    public static final String ALARMS_EXTRA = "alarms_extra";

    @SuppressWarnings("unused")
    public LoadAlarmsService() {
        this(TAG);
    }

    public LoadAlarmsService(String name){
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final List<Alarm> alarms = DatabaseHelper.getInstance(this).getAlarms();

        final Intent i = new Intent(ACTION_COMPLETE);
        i.putParcelableArrayListExtra(ALARMS_EXTRA, new ArrayList<>(alarms));
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);


    }

    public static void launchLoadAlarmsService(Context context) {
        final Intent launchLoadAlarmsServiceIntent = new Intent(context, LoadAlarmsService.class);
        context.startService(launchLoadAlarmsServiceIntent);
    }

}
