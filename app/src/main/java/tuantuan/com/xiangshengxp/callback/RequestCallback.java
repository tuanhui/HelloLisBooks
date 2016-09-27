package tuantuan.com.xiangshengxp.callback;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public interface RequestCallback<T> {
    void beforeRequest();

    void requestError(String msg);

    void requestComplete();

    void requestSuccess(T data);
}
