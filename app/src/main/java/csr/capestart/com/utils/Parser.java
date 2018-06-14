package csr.capestart.com.utils;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import csr.capestart.com.data.models.User;
import csr.capestart.com.extras.AppLog;

public final class Parser {

    private static final String TAG = "Parser";

    private Parser() {

    }

    public static User parseUser(JSONObject result) {
        User user = new User();
        try {
            JSONObject u = result.getJSONObject("user");
            user.setFirstName(u.getString("first_name"));
            user.setLastName(u.getString("last_name"));
            user.setMobile(u.getString("mobile"));
            user.setuId(u.getString("u_id"));
        } catch (JSONException e) {
            AppLog.error(TAG, "Caught with exception "+e.getMessage());
        }
        return user;
    }
}
