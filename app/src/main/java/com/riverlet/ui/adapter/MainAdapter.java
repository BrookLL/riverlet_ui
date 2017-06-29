package com.riverlet.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liu.riverlet.R;
import com.riverlet.ui.adapter.listener.OnItemClickListner;

import java.util.List;

/**
 * Created by liujian on 17-6-28.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private OnItemClickListner onItemClickListner;
    private LayoutInflater inflater;
    private List<String> datas;

    public MainAdapter(Context context,List<String> datas) {
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_main,parent,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        holder.text.setText(datas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListner != null) {
                    onItemClickListner.onItemClick(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        public MainViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
