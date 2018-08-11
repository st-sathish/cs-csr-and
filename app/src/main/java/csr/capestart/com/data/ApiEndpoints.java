package csr.capestart.com.data;

import csr.capestart.com.BuildConfig;

public final class ApiEndpoints {

    private ApiEndpoints() {

    }

    private static final String API_BASE_PATH = BuildConfig.BASE_URL + "/api/v1";

    public static final String IMAGE_BASE_PATH = BuildConfig.BASE_URL + "/images";

    public static final String POST_SIGNIN_API = API_BASE_PATH + "/signin.php";

    public static final String GET_CATEGORIES_API = API_BASE_PATH + "/category/get_categories.php";

    public static final String GET_COOKIE_ITEMS_API = API_BASE_PATH + "/items/get_items.php";

    public static final String GET_EXPIRED_ITEMS_API = API_BASE_PATH + "/items/get_expired_items.php";

    public static final String PUT_REGISTER_DEVICE_TOKEN_API = API_BASE_PATH + "/user/put_device_token.php";

    public static final String GET_DASHBOARD_META_DATA = API_BASE_PATH + "/dashboard.php";

    public static final String GET_NOTIFICATION_ITEMS = API_BASE_PATH + "/notifications.php";

    public static final String GET_EXPIRED_DATES_BY_GROUP_API = API_BASE_PATH + "/items/get_group_by_expired_dates.php";
}
