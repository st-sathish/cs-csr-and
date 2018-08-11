package csr.capestart.com.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import csr.capestart.com.data.models.Category;
import csr.capestart.com.data.models.Cookie;
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
            user.setEmail(u.getString("username"));
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

    public static List<Cookie> parseCookieItem(JSONArray items) {
        List<Cookie> cookies = new ArrayList<Cookie>();
        try {
            for(int i =0;i < items.length();i++) {
                JSONObject c = items.getJSONObject(i);
                Cookie cookie = new Cookie();
                cookie.setItemName(c.getString("item_name"));
                cookie.setBarcode(c.getString("barcode"));
                cookie.setExpiredDate(c.getString("expiry_date"));
                cookie.setItemId(c.getString("i_id"));
                cookie.setPurchasePrice(c.getString("purchase_price"));
                cookie.setCategory("category");
                cookies.add(cookie);
            }
        } catch (JSONException e) {
            AppLog.error(TAG, e.getMessage());
        }
        return cookies;
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
                expiredItem.setPurchasePrice(c.getString("purchase_price"));
                expiredItem.setCategory("category");
                expiredItems.add(expiredItem);
            }
        } catch (JSONException e) {
            AppLog.error(TAG, e.getMessage());
        }
        return expiredItems;
    }
}
