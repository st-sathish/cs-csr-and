package csr.capestart.com.data;

public class ApiEndpoints {

    private static final String DEV_HOST_URL = "http://192.168.43.134/csr";

    private static final String API_BASE_PATH = DEV_HOST_URL + "/api/v1";

    public static final String POST_SIGNIN_API = API_BASE_PATH + "/signin.php";

    public static final String GET_CATEGORIES_API = API_BASE_PATH + "/category/get_categories.php";

}
