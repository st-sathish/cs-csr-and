package csr.capestart.com.data;

public final class ApiEndpoints {

    private ApiEndpoints() {

    }

    private static final String DEV_HOST_URL = "http://192.168.43.134/csr";//http://10.0.2.2/csr";

    private static final String API_BASE_PATH = DEV_HOST_URL + "/api/v1";

    public static final String POST_SIGNIN_API = API_BASE_PATH + "/signin.php";

    public static final String GET_CATEGORIES_API = API_BASE_PATH + "/category/get_categories.php";

    public static final String GET_COOKIE_ITEMS_API = API_BASE_PATH + "/items/get_items.php";

    public static final String GET_EXPIRED_ITEMS_API = API_BASE_PATH + "/items/get_expired_items.php";

    public static final String POST_REGISTER_DEVICE_TOKEN_API = API_BASE_PATH + "/user/put_device_token.php";
}
