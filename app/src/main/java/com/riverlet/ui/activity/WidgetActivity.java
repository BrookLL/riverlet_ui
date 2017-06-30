package com.riverlet.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.liu.riverlet.R;
import com.riverlet.ui.base.BaseActivity;
import com.riverlet.ui.fragment.WidgetFragment;
import com.liu.riverletui.widget.RingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujian on 17-6-29.
 */

public class WidgetActivity extends BaseActivity implements View.OnClickListener {
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    private List<View> viewList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_widget;
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) bindId(R.id.viewPager);
        RingView ringView = new RingView(this);
        ringView.setOnClickListener(this);
        ringView.setData(new float[]{100f,100f,100f,100f,100f});
        viewList.add(ringView);
    }

    @Override
    protected void initData() {
        for (View view : viewList) {
            fragmentList.add(WidgetFragment.newInstance(view));
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view instanceof RingView){
            ((RingView) view).animStart();
        }
    }
}
