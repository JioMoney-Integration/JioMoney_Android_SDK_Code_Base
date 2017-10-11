package com.ril.jm_sdk.util;

import android.util.Patterns;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by COMPAQ on 02-08-2016.
 */
public class CommonUtility {
    private static final String TAG = CommonUtility.class.getSimpleName();
    private static final String AMOUNT_FORMAT = "%.2f";
    private static final String CURRENT_DATE_FORMAT = "yyyyMMddhhmmss";

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CURRENT_DATE_FORMAT, Locale.US);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    /**
     * Round to certain number of decimals
     *
     * @param value        double value
     * @return returns the string with 2 digit after decimal
     */
    public static String round(double value) {
        return String.format(Locale.US, AMOUNT_FORMAT, value);
    }

    public static String getJsonStringFromHashMap(Map<String, String> postData) {
        JSONObject json = new JSONObject(postData);
        return json.toString();
    }


    public static String getNumbersFromString(String str) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(str);
        if (m.find()) {
            android.util.Log.d(TAG, "SMS :" + m.group());
            return m.group();
        }
        return "";
    }

    public static boolean validateMobile(String mobile) {
        return Patterns.PHONE.matcher(mobile).matches();
    }


}