package csr.capestart.com.extras;

import android.util.Log;

/**
 * Created by CS39 on 1/22/2017.
 */

public final class AppLog {

    /** set false when moving to production */
    private static final boolean isError = true;
    private static final boolean isDebug = true;
    private static final boolean isInfoMessage = true;

    private AppLog() {}

    public static void error(String tag, String message) {
        if(isError)
            Log.e(tag, message);
    }

    public static void debug(String tag, String message) {
        if(isDebug)
            Log.e(tag, message);
    }

    public static void message(String tag, String message) {
        if(isInfoMessage)
            Log.e(tag, message);
    }
}