package com.example.myfavoritemovie.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by riza on 09/08/18.
 */

public class AppUtils {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String formatDate(String date) {

        String formattedDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;

        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf.applyPattern("dd MMM yyyy");
        formattedDate = sdf.format(d);

        return formattedDate;
    }

    public static String formatYear(String date){
        return !TextUtils.isEmpty(date)? date.substring(0,4): "Unknown";
    }

    public static String formatDesc(String desc){
        return (desc.length()>70)? desc.substring(0,70):desc;
    }

}
