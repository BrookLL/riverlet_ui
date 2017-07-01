package com.riverlet.ui.test.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.riverlet.ui.test.R;
import com.riverlet.ui.test.adapter.LoadingAdapter;
import com.riverlet.ui.test.base.BaseActivity;
import com.riverlet.ui.widget.loading.LoadingView;
import com.riverlet.ui.widget.loading.SimpleLoadingView;
import com.riverlet.ui.widget.loading.ThreeBallLoadingView;
import com.riverlet.ui.widget.recycler.GridDivider;

import java.util.ArrayList;
import java.util.List;


public class LoadingViewActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<LoadingView> loadingViewList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading_view;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new GridDivider());
    }

    @Override
    protected void initData() {
        loadingViewList.add(new ThreeBallLoadingView(this));
        SimpleLoadingView simpleLoadingView = new SimpleLoadingView(this);
        loadingViewList.add(simpleLoadingView);
        LoadingAdapter adapter = new LoadingAdapter(this, loadingViewList);
        recyclerView.setAdapter(adapter);
    }
}
