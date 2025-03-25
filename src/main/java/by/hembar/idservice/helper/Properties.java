package by.hembar.idservice.helper;

import java.util.List;

public class Properties {



    protected Properties(String host, Integer port, String idServiceHost, Integer idServicePort, String chatServiceHost, Integer chatServicePort, String newsServiceHost, Integer newsServicePort, String searchServiceHost, Integer searchServicePort, String storageServiceHost, Integer storageServicePort, String secretKey, Long sessionLifeTime, Long jwtLifeTime, Long userTimeInBlock, String adminKey, List<Long> adminIdList) {
        HOST = host;
        PORT = port;
        ID_SERVICE_HOST = idServiceHost;
        ID_SERVICE_PORT = idServicePort;
        CHAT_SERVICE_HOST = chatServiceHost;
        CHAT_SERVICE_PORT = chatServicePort;
        NEWS_SERVICE_HOST = newsServiceHost;
        NEWS_SERVICE_PORT = newsServicePort;
        SEARCH_SERVICE_HOST = searchServiceHost;
        SEARCH_SERVICE_PORT = searchServicePort;
        STORAGE_SERVICE_HOST = storageServiceHost;
        STORAGE_SERVICE_PORT = storageServicePort;
        SECRET_KEY = secretKey;
        SESSION_LIFE_TIME = sessionLifeTime;
        JWT_LIFE_TIME = jwtLifeTime;
        USER_TIME_IN_BLOCK = userTimeInBlock;
        ADMIN_KEY = adminKey;
        ADMIN_ID_LIST = adminIdList;
    }

    private static final class Helper {
        private static boolean instanceIsSet = false;
        private static Properties INSTANCE;
    }

    public static Properties get() {
        return Helper.INSTANCE;
    }



    protected static void setInstance(PropertiesBean propertiesBean) {
        if (!Helper.instanceIsSet) {
            Helper.INSTANCE = propertiesBean;
            Helper.instanceIsSet = true;
        }
    }

    public final String HOST;
    public final Integer PORT;
    public final String ID_SERVICE_HOST;
    public final Integer ID_SERVICE_PORT;
    public final String CHAT_SERVICE_HOST;
    public final Integer CHAT_SERVICE_PORT;
    public final String NEWS_SERVICE_HOST;
    public final Integer NEWS_SERVICE_PORT;
    public final String SEARCH_SERVICE_HOST;
    public final Integer SEARCH_SERVICE_PORT;
    public final String STORAGE_SERVICE_HOST;
    public final Integer STORAGE_SERVICE_PORT;
    public final String SECRET_KEY;
    public final Long SESSION_LIFE_TIME;
    public final Long JWT_LIFE_TIME;
    public final Long USER_TIME_IN_BLOCK;
    public final String ADMIN_KEY;
    public final List<Long> ADMIN_ID_LIST;

}
