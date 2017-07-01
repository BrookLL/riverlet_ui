package com.riverlet.ui.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.riverlet.ui.test.R;


public class WidgetFragment extends Fragment {
    private View view;

    public WidgetFragment() {
    }


    public static WidgetFragment newInstance(View view) {
        WidgetFragment fragment = new WidgetFragment();
        fragment.view = view;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_widget, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FrameLayout viewparent = (FrameLayout) view.findViewById(R.id.viewparent);
        viewparent.addView(this.view);
    }
}
