package com.example.riza.moviecatalogueuiux.utils;

import android.content.Context;

import com.example.riza.moviecatalogueuiux.receiver.AlarmReceiver;

public class AlarmHelper {

    private AlarmReceiver alarmReceiver;
    private Context context;

    private static final String DAILY_TIME = "07:00";
    private static final String RELEASE_TIME = "08:00";

    public AlarmHelper(Context context) {
        this.context = context;
        alarmReceiver = new AlarmReceiver();
    }

    public void setDailyReminder(boolean active){
        if(active){
            alarmReceiver.setRepeatingAlarm(context, AlarmReceiver.TYPE_DAILY,DAILY_TIME);
        }else {
            alarmReceiver.cancelAlarm(context, AlarmReceiver.TYPE_DAILY);
        }
    }

    public void setReleaseReminder(boolean active){
        if(active){
            alarmReceiver.setRepeatingAlarm(context, AlarmReceiver.TYPE_RELEASE,RELEASE_TIME );
        }else {
            alarmReceiver.cancelAlarm(context, AlarmReceiver.TYPE_RELEASE);
        }
    }

    public boolean isDailySet(){
        return alarmReceiver.isAlarmSet(context, AlarmReceiver.TYPE_DAILY);
    }

    public boolean isReleaseSet(){
        return alarmReceiver.isAlarmSet(context, AlarmReceiver.TYPE_RELEASE);
    }


}
