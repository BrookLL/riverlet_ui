package com.riverlet.ui.test.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import com.riverlet.ui.test.R;

/**
 * Created by liuj on 2016/1/5.
 */
public class SeekBarDialog {

    private Context context;
    private AlertDialog dialog;
    private SeekBar seekBar;
    private TextView progr;
    private OnProgressListener progressListener;


    public SeekBarDialog(Context context) {
        this.context =context;
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_seekbar);
        seekBar = (SeekBar) window.findViewById(R.id.seekBar);
        progr = (TextView) window.findViewById(R.id.progress);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress==0){
                    progr.setText("1");
                }else {
                    progr.setText(progress+"");
                }
                progressListener.onChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public OnProgressListener getProgressListener() {
        return progressListener;
    }

    public void setProgressListener(OnProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public interface OnProgressListener{
       void onChange(int progress);
    }
}
