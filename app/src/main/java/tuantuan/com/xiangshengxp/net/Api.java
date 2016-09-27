package tuantuan.com.xiangshengxp.net;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public class Api {
    public static final String BOOK_LIST_HOST = "http://api2.qingting.fm/";
    public static String getHost(int hostType) {
        switch(hostType) {
            case HostType.TYPE_BOOK:
                return BOOK_LIST_HOST;
        }
        return "";
    }
}
