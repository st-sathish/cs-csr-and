package csr.capestart.com.utils;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import csr.capestart.com.data.models.Category;
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

    public static void addParsedCategory(JSONArray categories, List<Category> categoryList) {
        try {
            for(int i =0;i < categories.length();i++) {
                JSONObject c = categories.getJSONObject(i);
                String categoryName = c.getString("name");
                AppLog.log(TAG, categoryName);
                if (!"".equals(categoryName)) {
                    Category category = new Category();
                    category.setCategoryName(categoryName);
                    categoryList.add(category);
                }
            }
        } catch (JSONException e) {
            AppLog.error(TAG, e.getMessage());
        }
    }

}
