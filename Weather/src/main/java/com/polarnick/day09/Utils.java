package com.polarnick.day09;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class Utils {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private static final String SUFFIX_OF_TRUNCATION = "...";

    public static String truncateString(String string, int limit) {
        if (string.length() > limit) {
            return string.substring(0, limit - SUFFIX_OF_TRUNCATION.length()) + SUFFIX_OF_TRUNCATION;
        } else {
            return string;
        }
    }

}
