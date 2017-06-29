package com.liu.riverlet_ui.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.liu.riverlet_ui.R;
import com.liu.riverlet_ui.ui.base.BaseActivity;
import com.liu.riverlet_ui.ui.fragment.WidgetFragment;
import com.liu.riverletui.widget.RingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujian on 17-6-29.
 */

public class WidgetActivity extends BaseActivity {
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_widget;
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) bindId(R.id.viewPager);
    }

    @Override
    protected void initData() {
        fragmentList.add(WidgetFragment.newInstance(new RingView(this)));
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
}
