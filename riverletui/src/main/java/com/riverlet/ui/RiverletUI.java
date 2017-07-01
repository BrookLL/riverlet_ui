package com.riverlet.ui;

import android.content.Context;

/**
 * Created by liujian on 17-7-1.
 */

public class RiverletUI {
    public static RiverletUI riverletUI;
    private Context context;

    private RiverletUI() {

    }

    public static RiverletUI get() {
        if (riverletUI == null) {
            synchronized (RiverletUI.class) {
                if (riverletUI == null) {
                    riverletUI = new RiverletUI();
                }
            }
        }
        return riverletUI;
    }

    public void init(Context context) {
        this.context = context;
    }

    public void uninit() {
        this.context = null;
    }

    public Context getContext() {
        if (context == null) {
            throw new NullPointerException("RiverletUI is not init ！");
        }
        return context;
    }
}
