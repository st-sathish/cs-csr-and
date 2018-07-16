package csr.capestart.com.utils;

public final class StringUtils {

    private StringUtils() {
        // avoid to create instance outside
    }

    public static boolean isNotEmpty(String aString) {
        return !isEmpty(aString);
    }

    public static boolean isEmpty(String aString) {
        boolean isEmpty = true;

        if ((null != aString) && (aString.trim().length() > 0)) {
            isEmpty = false;
        }
        return isEmpty;
    }
}
