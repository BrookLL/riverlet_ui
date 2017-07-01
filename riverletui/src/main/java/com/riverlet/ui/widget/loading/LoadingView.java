package com.riverlet.ui.widget.loading;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liujian on 17-7-1.
 */

public abstract class LoadingView extends View {
    protected boolean isAutoPlay = false;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
    }
    public abstract void animStart();
    public abstract void animStop();

}
