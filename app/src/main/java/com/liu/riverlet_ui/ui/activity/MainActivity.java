package com.liu.riverlet_ui.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liu.riverlet_ui.R;
import com.liu.riverlet_ui.ui.adapter.MainAdapter;
import com.liu.riverlet_ui.ui.adapter.listener.OnItemClickListner;
import com.liu.riverlet_ui.ui.base.BaseActivity;
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
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position){
            case 0:

                break;
        }
    }
}
