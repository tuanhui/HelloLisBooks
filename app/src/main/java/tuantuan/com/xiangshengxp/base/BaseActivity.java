package tuantuan.com.xiangshengxp.base;


import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import tuantuan.com.xiangshengxp.BuildConfig;
import tuantuan.com.xiangshengxp.R;

/**
 * Created by chentuanhui on 2016/9/23.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    protected boolean mEnableSlidr;
    protected int mMenuId;
    protected int mToolbarIndicator;
    protected int mToolbarTitle;
    protected int mMenuDefaultCheckedItem;
    protected ViewGroup mContentView;
    // 将代理类通用行为抽出来
    protected T mPresenter;
    private int mContentViewId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
            ActivityFragmentInject annotation = this.getClass().getAnnotation(ActivityFragmentInject.class);
            mContentViewId = annotation.contentViewId();
            mEnableSlidr = annotation.enableSlidr();
            mMenuId = annotation.menuId();
            mToolbarIndicator = annotation.toolbarIndicator();
            mToolbarTitle = annotation.toolbarTitle();
            mMenuDefaultCheckedItem = annotation.menuDefaultCheckedItem();
        } else {
            throw new RuntimeException("");
        }

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
        mContentView = (ViewGroup) LayoutInflater.from(this).inflate(mContentViewId, null);
        setContentView(mContentView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mContentView.setFitsSystemWindows(false);
            mContentView.setClipToPadding(false);
        }
        initToolbar();
        initView();
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }
    protected void showSnackbar(String msg) {
        Snackbar.make(mContentView, msg, Snackbar.LENGTH_SHORT).show();
    }
    protected abstract void initView();
}
