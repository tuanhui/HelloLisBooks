package tuantuan.com.xiangshengxp.module.book.model;

import rx.Subscription;
import tuantuan.com.xiangshengxp.callback.RequestCallback;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public interface IBookListInteractor<T> {
    Subscription requestBookList(RequestCallback<T> callback, String order, int id, int currentPage, int pageSize);
}
