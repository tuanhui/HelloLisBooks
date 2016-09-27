package tuantuan.com.xiangshengxp.net;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ClassName: HostType<p>
 * Author:oubowu<p>
 * Fuction: 请求数据host的类型<p>
 * CreateDate:2016/2/13 23:50<p>
 * UpdateUser:<p>
 * UpdateDate:<p>
 */
public class HostType {

    /**
     * 多少种Host类型
     */
    public static final int TYPE_COUNT = 1;

    /**
     * 有声读物
     */
    @HostTypeChecker
    public static final int TYPE_BOOK = 1;

    /**
     * /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({TYPE_BOOK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {
    }
}
