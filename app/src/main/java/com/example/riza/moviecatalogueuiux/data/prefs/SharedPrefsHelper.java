package com.example.riza.moviecatalogueuiux.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {

    private static final String MOVIE_PREFS = "mov_prefs";
    private SharedPreferences preferences;

    public SharedPrefsHelper(Context context) {
        preferences = context.getSharedPreferences(MOVIE_PREFS, Context.MODE_PRIVATE);
    }

    public void setString(String key, String value){
        preferences.edit().putString(key,value).apply();
    }

    public String getString(String key, String def){
        return preferences.getString(key,def);
    }

    public void setBoolean(String key, boolean value){
        preferences.edit().putBoolean(key,value).apply();
    }

    public boolean getBoolean(String key, boolean def){
        return preferences.getBoolean(key, def);
    }

}
