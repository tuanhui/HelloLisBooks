package tuantuan.com.xiangshengxp.module.book.model;

import rx.Observable;
import rx.Subscription;
import tuantuan.com.xiangshengxp.base.BaseSubscriber;
import tuantuan.com.xiangshengxp.bean.BookResult;
import tuantuan.com.xiangshengxp.callback.RequestCallback;
import tuantuan.com.xiangshengxp.net.HostType;
import tuantuan.com.xiangshengxp.net.manager.RetrofitManager;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public class IBookListInteractorImpl implements IBookListInteractor<BookResult> {
    @Override
    public Subscription requestBookList(RequestCallback<BookResult> callback, String order, int id, int currentPage, int pageSize) {
        return RetrofitManager.getInstance(HostType.TYPE_BOOK)
                .getBookListObservable("bydefault", id, currentPage, pageSize).subscribe(new BaseSubscriber<BookResult>(callback));
    }
}
