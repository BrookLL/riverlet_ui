package com.riverlet.ui.test.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.riverlet.ui.test.R;
import com.riverlet.ui.test.adapter.MainAdapter;
import com.riverlet.ui.test.adapter.listener.OnItemClickListner;
import com.riverlet.ui.test.base.BaseActivity;
import com.riverlet.ui.widget.RingView;
import com.riverlet.ui.widget.loading.LoadingView;
import com.riverlet.ui.widget.recycler.ListDivider;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements OnItemClickListner {

    private RecyclerView recyclerView;
    private List<String> datas = Arrays.asList(
            RingView.class.getSimpleName(),
            LoadingView.class.getSimpleName()
    );

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) bindId(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ListDivider(1, "#ffffff"));
    }

    @Override
    protected void initData() {

        MainAdapter adapter = new MainAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListner(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                startActivity(WidgetActivity.class);
                break;
            case 1:
                startActivity(LoadingViewActivity.class);
                break;
        }
    }
}
