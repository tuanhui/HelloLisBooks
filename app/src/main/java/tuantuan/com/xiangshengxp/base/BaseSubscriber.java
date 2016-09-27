package tuantuan.com.xiangshengxp.base;

import android.support.annotation.CallSuper;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import tuantuan.com.xiangshengxp.app.App;
import tuantuan.com.xiangshengxp.callback.RequestCallback;
import tuantuan.com.xiangshengxp.utils.KLog;
import tuantuan.com.xiangshengxp.utils.NetUtil;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public class BaseSubscriber<T> extends Subscriber<T> {
    private RequestCallback<T> mRequestCallback;

    public BaseSubscriber(RequestCallback<T> callback) {
        mRequestCallback = callback;
    }

    @CallSuper
    @Override
    public void onCompleted() {
        if (mRequestCallback != null) {
            mRequestCallback.requestComplete();
        }
    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        if (mRequestCallback != null) {
            String errorMsg = null;
            if (e instanceof HttpException) {
                switch (((HttpException) e).code()) {
                    case 403:
                        errorMsg = "没有权限访问此链接！";
                        break;
                    case 504:
                        if (!NetUtil.isConnected(App.getContext())) {
                            errorMsg = "没有联网哦！";
                        } else {
                            errorMsg = "网络连接超时！";
                        }
                        break;
                    default:
                        errorMsg = ((HttpException) e).message();
                        break;
                }
            } else if (e instanceof UnknownHostException) {
                errorMsg = "不知名主机";
            } else if (e instanceof SocketTimeoutException) {
                errorMsg = "网络连接超时！";
            }
            KLog.e(e.toString());
            mRequestCallback.requestError(errorMsg);
        }
    }

    @CallSuper
    @Override
    public void onNext(T t) {
        if (mRequestCallback != null) {
            mRequestCallback.requestSuccess(t);
        }
    }
}
