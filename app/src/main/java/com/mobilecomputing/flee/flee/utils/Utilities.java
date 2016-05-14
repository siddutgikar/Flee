package com.mobilecomputing.flee.flee.utils;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by siddh on 4/22/2016.
 */
public class Utilities {



    public static long getCurrentTimeinEpoc(String date) {
        long then = 0;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(date));
            then = cal.getTimeInMillis();

        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            then = cal.getTimeInMillis();

        }
        return then;
    }


    public static long getNextTimeinEpoc(String date) {
        long then = 0;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(date));
            long now = cal.getTimeInMillis();
            cal.add(Calendar.DAY_OF_YEAR, 1);

            then = cal.getTimeInMillis();

        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            long now = cal.getTimeInMillis();
            cal.add(Calendar.DAY_OF_YEAR, 1);

            then = cal.getTimeInMillis();

        }
        return then;
    }



}
