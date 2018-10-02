package com.example.riza.moviecatalogueuiux.data.prefs;


import android.content.Context;

public class MyPreference {

    private static final String F_LAUNCH = "flaunch";

    private SharedPrefsHelper prefs;

    public MyPreference(Context ctx) {
        prefs = new SharedPrefsHelper(ctx);
    }

    public boolean isFirstLaunch(){
        return prefs.getBoolean(F_LAUNCH, true);
    }

    public void setisFirstLaunch(boolean val){
        prefs.setBoolean(F_LAUNCH, val);
    }




}
