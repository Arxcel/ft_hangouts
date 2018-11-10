package com.example.arxcel.ft_hangouts;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    private int numStarted = 0;
    private static Context context;
    SharedPreferences mPrefs;
    private final String format = "dd/M/yyyy hh:mm:ss";
    SimpleDateFormat mDateFormat = new SimpleDateFormat(format);

    public static Context getAppContext() {
        return App.context;
    }

    @Override public void onActivityStarted(Activity a) {
        if (numStarted == 0) {
            String date = mPrefs.getString("wentToBackground", "");
            try {
                if (!date.isEmpty())
                {
                    Date d = mDateFormat.parse(date);
                    printDifference(d, Calendar.getInstance().getTime());
                }
            }catch(ParseException ex){
            }
        }
        numStarted++;
    }

    @Override public void onActivityStopped(Activity a) {
        numStarted--;
        if (numStarted == 0) {
            Date currentTime = Calendar.getInstance().getTime();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString("wentToBackground", mDateFormat.format(currentTime));
            editor.apply();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        super.onCreate();
        App.context = getApplicationContext();
        registerActivityLifecycleCallbacks(this);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli - 12;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        Toast.makeText(getApplicationContext(), "Background time:" + elapsedDays + "d" + elapsedHours + "h" + elapsedMinutes + "m" + elapsedSeconds + "s", Toast.LENGTH_LONG).show();
    }
}
