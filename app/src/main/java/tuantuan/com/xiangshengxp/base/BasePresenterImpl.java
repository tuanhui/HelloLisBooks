package tuantuan.com.xiangshengxp.base;

import rx.Subscription;
import tuantuan.com.xiangshengxp.callback.RequestCallback;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public class BasePresenterImpl<T extends BaseView, V> implements BasePresenter, RequestCallback<V> {
    protected Subscription mSubscription;
    protected T mView;

    public BasePresenterImpl(T view) {
        mView = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if(mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mView = null;
    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void requestError(String msg) {
        mView.toast(msg);
        mView.hideProgress();
    }

    @Override
    public void requestComplete() {

    }

    @Override
    public void requestSuccess(V data) {

    }
}
