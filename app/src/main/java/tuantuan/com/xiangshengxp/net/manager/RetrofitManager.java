package tuantuan.com.xiangshengxp.net.manager;


import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import tuantuan.com.xiangshengxp.app.App;
import tuantuan.com.xiangshengxp.base.BaseSchedulerTransformer;
import tuantuan.com.xiangshengxp.bean.BookList;
import tuantuan.com.xiangshengxp.bean.BookResult;
import tuantuan.com.xiangshengxp.net.Api;
import tuantuan.com.xiangshengxp.net.HostType;
import tuantuan.com.xiangshengxp.net.service.BookService;
import tuantuan.com.xiangshengxp.utils.NetUtil;

/**
 * ClassName: RetrofitManager<p>
 * Author: oubowu<p>
 * Fuction: Retrofit请求管理类<p>
 * CreateDate:2016/2/13 20:34<p>
 * UpdateUser:<p>
 * UpdateDate:<p>
 */
public class RetrofitManager {

    //设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
    private static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static volatile OkHttpClient sOkHttpClient;
    // 管理不同HostType的单例
    private static SparseArray<RetrofitManager> sInstanceManager = new SparseArray<>(HostType.TYPE_COUNT);
    private BookService mBookService;
    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Log.e("TAG", "请求网址: " + request.url());

            if (!NetUtil.isConnected(App.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                KLog.e("没有网络");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isConnected(App.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached," + CACHE_STALE_SEC).removeHeader("Pragma").build();
            }
        }
    };

    // 打印返回的json数据拦截器
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request request = chain.request();
            final Response response = chain.proceed(request);

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e("");
                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
                KLog.v("--------------------------------------------开始打印返回数据----------------------------------------------------");
                KLog.json(buffer.clone().readString(charset));
                KLog.v("--------------------------------------------结束打印返回数据----------------------------------------------------");
                File file = App.getContext().getCacheDir();
                file = new File(file, "11111111111111.txt");
                KLog.e(file.getAbsolutePath());
                FileOutputStream out = new FileOutputStream(file);
                out.write(buffer.clone().readString(charset).getBytes());
                out.close();
            }

            return response;
        }
    };

    private RetrofitManager() {
    }

    private RetrofitManager(@HostType.HostTypeChecker int hostType) {

        Retrofit.Builder client = new Retrofit.Builder().baseUrl(Api.getHost(hostType)).client(getOkHttpClient());
        client.addConverterFactory(JacksonConverterFactory.create());
        client.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        Retrofit retrofit = client.build();
        mBookService = retrofit.create(BookService.class);
    }

    /**
     * 获取单例
     *
     * @param hostType host类型
     * @return 实例
     */
    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager instance = sInstanceManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitManager(hostType);
            sInstanceManager.put(hostType, instance);
            return instance;
        } else {
            return instance;
        }
    }

    // 配置OkHttpClient
    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (sOkHttpClient == null) {
                    // OkHttpClient配置是一样的,静态创建一次即可
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cache(cache);
                    builder.addNetworkInterceptor(mRewriteCacheControlInterceptor);
                    // .addInterceptor(mRewriteCacheControlInterceptor)
                    builder.addInterceptor(mLoggingInterceptor);
                    builder.retryOnConnectionFailure(true);
                    builder.connectTimeout(30, TimeUnit.SECONDS);
                    sOkHttpClient = builder.build();

                }
            }
        }
        return sOkHttpClient;
    }

    /**
     * 根据网络状况获取缓存的策略
     *
     * @return
     */
    @NonNull
    private String getCacheControl() {
        return NetUtil.isConnected(App.getContext()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }

    /**

     */
    public Observable<BookResult> getBookListObservable(String order, int id, int currentPage, int pageSize) {
        Observable<BookResult> result
                = mBookService.getBookList(getCacheControl(), order, id, currentPage, pageSize).compose(new BaseSchedulerTransformer<BookResult>());

        return result;
    }
}
