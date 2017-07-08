package com.riverlet.ui.test.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.riverlet.ui.test.R;
import com.riverlet.ui.test.adapter.MainAdapter;
import com.riverlet.ui.test.adapter.listener.OnItemClickListner;
import com.riverlet.ui.test.base.BaseActivity;
import com.riverlet.ui.widget.RingView;
import com.riverlet.ui.widget.loading.LoadingView;
import com.riverlet.ui.widget.recycler.ElasticRecyclerView;
import com.riverlet.ui.widget.recycler.ListDivider;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements OnItemClickListner {

    private RecyclerView recyclerView;
    public List<String> datas = Arrays.asList(
            LoadingView.class.getSimpleName(),
            "Calendar(日历)",
            "DrawingView(画板)",
            RingView.class.getSimpleName()+"环形图",
            "BetterRadio and SwitchView(选项卡和开关)"
    );

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        recyclerView = ((ElasticRecyclerView) bindId(R.id.recyclerView)).getRecyclerView();
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
                startActivity(LoadingViewActivity.class);
                break;
            case 1:
                startActivity(CalendarActivity.class);
                break;
            case 2:
                startActivity(DrawingviewActivity.class);
                break;
            case 3:
                goWidgetActivity(0);
                break;
            case 4:
                goWidgetActivity(1);
                break;
        }
    }

    protected void goWidgetActivity(int page) {
        Intent intent = new Intent(this, WidgetActivity.class);
        intent.putExtra(WidgetActivity.PAGE, page);
        intent.putExtra(WidgetActivity.TITLE, datas.get(page + 2));
        startActivity(intent);
    }
}
