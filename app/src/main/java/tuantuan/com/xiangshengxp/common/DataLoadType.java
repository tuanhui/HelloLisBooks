package tuantuan.com.xiangshengxp.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public class DataLoadType {
    public static final int TYPE_REFRESH_SUCCESS = 1;
    public static final int TYPE_REFRESH_FAIL = 2;
    public static final int TYPE_LOAD_MORE_SUCCESS = 3;
    public static final int TYPE_LOAD_MORE_FAIL = 4;

    /**
     * IntDef保证类型安全
     */
    @IntDef({TYPE_REFRESH_SUCCESS, TYPE_REFRESH_FAIL, TYPE_LOAD_MORE_SUCCESS, TYPE_LOAD_MORE_FAIL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataLoadTypeChecker {

    }
}
