package csr.capestart.com.utils;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import csr.capestart.com.data.models.Category;
import csr.capestart.com.data.models.CookieItem;
import csr.capestart.com.data.models.ExpiredItem;
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
            user.setEmail(u.getString("email"));
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

    public static List<CookieItem> parseCookieItem(JSONArray items) {
        List<CookieItem> cookieItems = new ArrayList<CookieItem>();
        try {
            for(int i =0;i < items.length();i++) {
                JSONObject c = items.getJSONObject(i);
                CookieItem cookieItem = new CookieItem();
                cookieItem.setItemName(c.getString("item_name"));
                cookieItem.setBarcode(c.getString("barcode"));
                cookieItem.setExpiredDate(c.getString("expiry_date"));
                cookieItem.setItemId(c.getString("i_id"));
                cookieItem.setPrice(c.getString("price"));
                cookieItem.setCategory("category");
                cookieItems.add(cookieItem);
            }
        } catch (JSONException e) {
            AppLog.error(TAG, e.getMessage());
        }
        return cookieItems;
    }

    public static List<ExpiredItem> parseExpiredItem(JSONArray items) {
        List<ExpiredItem> expiredItems = new ArrayList<ExpiredItem>();
        try {
            for(int i =0;i < items.length();i++) {
                JSONObject c = items.getJSONObject(i);
                ExpiredItem expiredItem = new ExpiredItem();
                expiredItem.setItemName(c.getString("item_name"));
                expiredItem.setBarcode(c.getString("barcode"));
                expiredItem.setItemId(c.getString("i_id"));
                expiredItem.setPrice(c.getString("price"));
                expiredItem.setCategory("category");
                expiredItems.add(expiredItem);
            }
        } catch (JSONException e) {
            AppLog.error(TAG, e.getMessage());
        }
        return expiredItems;
    }
}
