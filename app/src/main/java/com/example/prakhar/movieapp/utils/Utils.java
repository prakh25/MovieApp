package com.example.prakhar.movieapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import timber.log.Timber;

import static org.joda.time.format.DateTimeFormat.forPattern;

/**
 * Created by Prakhar on 2/24/2017.
 */

public class Utils {

    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                Timber.i(simCountry);
                return simCountry.toLowerCase(Locale.US);
            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return null;
    }

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getTomorrowDate() {
        DateTimeFormatter formatter = forPattern("yyyy-MM-dd").withLocale(Locale.US);
        return LocalDateTime.fromCalendarFields(Calendar.getInstance()).plusDays(1).toString(formatter);
    }

    public static String minDate(String currentDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, -30);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String comingSoonDate(String currentDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, 45);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String currencyConverter(Double number) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(number);
    }

    public static int getReleaseYear(String date) {
        DateTimeFormatter formatter = forPattern("yyyy-MM-dd").withLocale(Locale.US);
        LocalDate date1 = formatter.parseLocalDate(date);
        return date1.getYear();
    }

    public static String getReleaseDate(String date) {
        DateTimeFormatter formatter = forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withLocale(Locale.US);
//        DateTimeFormatter formatter = forPattern("yyyy-MM-dd").withLocale(Locale.US);
        LocalDate date1 = formatter.parseLocalDate(date);
        DateTimeFormatter dateTimeFormatter = forPattern("dd MMM yyyy").withLocale(Locale.US);
        return dateTimeFormatter.print(date1);
    }

    public static boolean isScreenW(int widthDp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        return screenWidth >= widthDp;
    }

    public static String getDate(String date) {
        DateTimeFormatter formatter = forPattern("yyyy-MM-dd").withLocale(Locale.US);
        LocalDate date1 = formatter.parseLocalDate(date);
        DateTimeFormatter dateTimeFormatter = forPattern("dd MMM yyyy").withLocale(Locale.US);
        return dateTimeFormatter.print(date1);
    }

    public static int dpToPx(int dp, Context context) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, r.getDisplayMetrics()));
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int)(dpWidth / 180);
        return  noOfColumns;
    }

    public static int getDarkColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        return Color.HSVToColor(hsv);
    }
}

