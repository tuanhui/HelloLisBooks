package tuantuan.com.xiangshengxp.net.service;


import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;
import tuantuan.com.xiangshengxp.bean.BookList;
import tuantuan.com.xiangshengxp.bean.BookResult;

/**
 * Created by chentuanhui on 2016/9/23.
 */

public interface BookService {
    //order:bydefault
    //http://api2.qingting.fm/v6/media/categories/527/channels/order/bydefault/0/attr/855/curpage/1/pagesize/30
    @GET("v6/media/categories/527/channels/order/{order}/0/attr/{id}/curpage/{curpage}/pagesize/{pagesize}")
    Observable<BookResult> getBookList(@Header("Cache-Control") String cacheControl, @Path("order") String order, @Path("id") int id, @Path("curpage") int curpage, @Path("pagesize") int pagesize);
}
