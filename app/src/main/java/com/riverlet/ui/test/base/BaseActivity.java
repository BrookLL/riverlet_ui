package com.riverlet.ui.test.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by liujian on 17-6-28.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    public View bindId(@IdRes int id){
        return findViewById(id);
    }
    protected void startActivity(Class<? extends BaseActivity> activityClass){
        startActivity(new Intent(this,activityClass));
    }
}
