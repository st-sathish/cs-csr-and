package csr.capestart.com.extras;

import android.content.Context;
import android.content.SharedPreferences;

import csr.capestart.com.data.models.User;

public final class SessionStore {

    public static User user = null;

    private static final String CACHE_KEY = "cache";

    private static final String KEY_USER_TOKEN = "KEY_USER_TOKEN";

    public static void saveToken(Context context, String aToken) {
        SharedPreferences.Editor userData = context.getSharedPreferences(CACHE_KEY, Context.MODE_PRIVATE).edit();
        userData.putString(KEY_USER_TOKEN, aToken);
        userData.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences userData = context.getSharedPreferences(CACHE_KEY, Context.MODE_PRIVATE);
        return userData.getString(KEY_USER_TOKEN, "");
    }
}
