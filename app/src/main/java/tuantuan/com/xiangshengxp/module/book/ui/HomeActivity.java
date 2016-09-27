package tuantuan.com.xiangshengxp.module.book.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tuantuan.com.xiangshengxp.R;
import tuantuan.com.xiangshengxp.base.ActivityFragmentInject;
import tuantuan.com.xiangshengxp.base.BaseActivity;
import tuantuan.com.xiangshengxp.base.BaseSpacesItemDecoration;
import tuantuan.com.xiangshengxp.bean.BookList;
import tuantuan.com.xiangshengxp.common.DataLoadType;
import tuantuan.com.xiangshengxp.module.book.presenter.IBookPresenterImpl;
import tuantuan.com.xiangshengxp.module.book.view.IBookListView;
import tuantuan.com.xiangshengxp.net.manager.KLog;
import tuantuan.com.xiangshengxp.utils.MeasureUtil;

@ActivityFragmentInject(contentViewId = R.layout.activity_home)
public class HomeActivity extends BaseActivity implements IBookListView {

    private RecyclerView mRecyclerView;
    private List<BookList> mBooks;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mAdapter = new RecyclerView.Adapter<MyHolder>() {
            @Override
            public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.item_book_list, parent, false);
                return new MyHolder(view);
            }

            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                BookList item = mBooks.get(position);
                holder.itemTitle.setText(item.title);
                holder.itemDes.setText(item.description);
            }

            @Override
            public int getItemCount() {
                KLog.e("getitemcount");
                return mBooks == null ? 0 : mBooks.size();
            }
        };

        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(this, 8)));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter = new IBookPresenterImpl(this, "bydefault", 855, 1, 30);
    }

    @Override
    public void updateBookList(List<BookList> books, @DataLoadType.DataLoadTypeChecker int type) {
        switch (type) {
            case DataLoadType.TYPE_REFRESH_SUCCESS:

                mBooks = books;
                mAdapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private class MyHolder extends RecyclerView.ViewHolder {
        public ImageView itemLogo;
        public TextView itemTitle;
        public TextView itemDes;

        public MyHolder(View itemView) {
            super(itemView);
            itemLogo = (ImageView) itemView.findViewById(R.id.concept_cover_img);
            itemTitle = (TextView) itemView.findViewById(R.id.concept_title);
            itemDes = (TextView) itemView.findViewById(R.id.concept_content);
        }

    }
}
