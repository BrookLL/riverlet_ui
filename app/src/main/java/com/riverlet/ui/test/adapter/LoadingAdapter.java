package com.riverlet.ui.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.riverlet.ui.util.DensityUtil;
import com.riverlet.ui.widget.loading.LoadingView;
import com.riverlet.ui.widget.loading.SimpleLoadingView;

import java.util.List;

/**
 * Created by liujian on 17-7-1.
 */

public class LoadingAdapter extends RecyclerView.Adapter<LoadingAdapter.LoadingViewHolder> {
    private Context context;
    private List<LoadingView> viewList;
    private LinearLayout.LayoutParams layoutParams;

    public LoadingAdapter(Context context, List<LoadingView> viewList) {
        this.context = context;
        this.viewList = viewList;
        layoutParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(60), DensityUtil.dip2px(20));
    }

    @Override
    public LoadingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(150)));
        linearLayout.setBackgroundColor(0xf0f0f0f0);
        linearLayout.setGravity(Gravity.CENTER);
        return new LoadingViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(LoadingViewHolder holder, int position) {
        LoadingView loadingView = viewList.get(position);
        holder.parent.addView(loadingView, layoutParams);
        loadingView.animStart();
        if (loadingView instanceof SimpleLoadingView){
            holder.parent.setBackgroundColor(0xff199dff);
        }
    }

    @Override
    public int getItemCount() {
        return viewList == null ? 0 : viewList.size();
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parent;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            parent = (LinearLayout) itemView;
        }
    }
}
