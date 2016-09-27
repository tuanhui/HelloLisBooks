package tuantuan.com.xiangshengxp.module.book.presenter;

import tuantuan.com.xiangshengxp.base.BasePresenterImpl;
import tuantuan.com.xiangshengxp.bean.BookResult;
import tuantuan.com.xiangshengxp.common.DataLoadType;
import tuantuan.com.xiangshengxp.module.book.model.IBookListInteractorImpl;
import tuantuan.com.xiangshengxp.module.book.view.IBookListView;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public class IBookPresenterImpl extends BasePresenterImpl<IBookListView, BookResult> implements IBookPresenter {
    protected String mOrder;
    protected int mId;
    protected int mCurrentPage;
    protected int mPageSize;
    protected IBookListInteractorImpl mBookListInteractor;
    private boolean mHasInit;
    private boolean mIsRefresh = true;

    public IBookPresenterImpl(IBookListView view, String order, int id, int currentPage, int pageSize) {
        super(view);
        mBookListInteractor = new IBookListInteractorImpl();
        mSubscription = mBookListInteractor.requestBookList(this, order, id, currentPage, pageSize);
        mOrder = order;
        mId = id;
        mCurrentPage = currentPage;
        mPageSize = pageSize;
    }

    @Override
    public void refreshData() {
        mCurrentPage = 0;
        mIsRefresh = true;
        mSubscription = mBookListInteractor.requestBookList(this, mOrder, mId, mCurrentPage, mPageSize);
    }

    @Override
    public void loadMore() {
        mIsRefresh = false;
        mSubscription = mBookListInteractor.requestBookList(this, mOrder, mId, mCurrentPage, mPageSize);
    }

    @Override
    public void beforeRequest() {
        if (!mHasInit) {
            mView.showProgress();
        }
    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
        mView.updateBookList(null, mIsRefresh ? DataLoadType.TYPE_REFRESH_FAIL : DataLoadType.TYPE_LOAD_MORE_FAIL);
    }

    @Override
    public void requestSuccess(BookResult result) {
        mHasInit = true;
        if (result != null && result.data != null) {
            mView.updateBookList(result.data, mIsRefresh ? DataLoadType.TYPE_REFRESH_SUCCESS : DataLoadType.TYPE_LOAD_MORE_SUCCESS);
        }
    }
}
