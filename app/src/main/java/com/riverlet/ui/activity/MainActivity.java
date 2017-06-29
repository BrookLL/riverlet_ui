package com.riverlet.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liu.riverlet.R;
import com.riverlet.ui.adapter.MainAdapter;
import com.riverlet.ui.adapter.listener.OnItemClickListner;
import com.riverlet.ui.base.BaseActivity;
import com.liu.riverletui.widget.RingView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements OnItemClickListner {

    private RecyclerView recyclerView;
    private List<String> datas = Arrays.asList(RingView.class.getSimpleName());

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) bindId(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter adapter = new MainAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListner(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, WidgetActivity.class));
                break;
        }
    }
}
