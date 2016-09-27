package tuantuan.com.xiangshengxp.module.book.view;

import java.util.List;

import tuantuan.com.xiangshengxp.base.BaseView;
import tuantuan.com.xiangshengxp.bean.BookList;
import tuantuan.com.xiangshengxp.common.DataLoadType;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public interface IBookListView extends BaseView {
    void updateBookList(List<BookList> books, @DataLoadType.DataLoadTypeChecker int type);
}
