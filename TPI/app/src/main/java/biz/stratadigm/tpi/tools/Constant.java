package biz.stratadigm.tpi.tools;

/**
 * Created by tamara on 12/2/16.
 * Class which save constants in aplication
 */
import biz.stratadigm.tpi.BuildConfig;

public class Constant {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    // URL for uploading and sending request on server
    public static final String UPLOAD = BASE_URL + "/upload/";

    public static String USER = BASE_URL + "/user";
    public static String VENUES = BASE_URL + "/venue";
    public static String THALIS = BASE_URL + "/thali";

    public static String USERLIST = BASE_URL + "/users";
    public static String VENUESLIST = BASE_URL + "/venues";
    public static String THALISLIST = BASE_URL + "/thalis";
    public static String VENUESLISTFORTHALI = BASE_URL + "/thalis?venue=";

    public static String TEST = BASE_URL + "/hello";

    public static String TAG = "tpi";
}

