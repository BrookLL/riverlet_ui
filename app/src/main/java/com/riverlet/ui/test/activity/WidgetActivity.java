package com.riverlet.ui.test.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.riverlet.ui.test.R;
import com.riverlet.ui.test.base.BaseActivity;
import com.riverlet.ui.test.fragment.WidgetFragment;
import com.riverlet.ui.widget.RingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujian on 17-6-29.
 */

public class WidgetActivity extends BaseActivity implements View.OnClickListener {
    public static final String PAGE = "page";
    public static final String TITLE = "title";
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
        ringView.setData(new float[]{100f, 100f, 100f, 100f, 100f});
        viewList.add(ringView);

        View view = LayoutInflater.from(this).inflate(R.layout.layout_better_radio_and_switch, null, false);
        viewList.add(view);

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

        int page = getIntent().getIntExtra(PAGE, 0);
        viewPager.setCurrentItem(page);
        String title = getIntent().getStringExtra(TITLE);
        setTitle(title);

    }

    @Override
    public void onClick(View view) {
        if (view instanceof RingView) {
            ((RingView) view).animStart();
        }
    }
}
