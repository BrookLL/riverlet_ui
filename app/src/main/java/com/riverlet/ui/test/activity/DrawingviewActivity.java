package com.riverlet.ui.test.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.riverlet.ui.test.R;
import com.riverlet.ui.test.base.BaseActivity;
import com.riverlet.ui.test.widget.SeekBarDialog;
import com.riverlet.ui.widget.DrawingView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawingviewActivity extends BaseActivity {
    DrawingView drawingView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_drawingview;
    }

    @Override
    protected void initView() {
        drawingView = (DrawingView) findViewById(R.id.drawingView);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, 1, "画笔颜色");
        menu.add(Menu.NONE, 2, 2, "画笔大小");
        menu.add(Menu.NONE, 3, 3, "画笔透明度");
        menu.add(Menu.NONE, 4, 4, "图形选择");
        menu.add(Menu.NONE, 5, 5, "上一步");
        menu.add(Menu.NONE, 6, 6, "下一步");
        menu.add(Menu.NONE, 7, 7, "清空");
        menu.add(Menu.NONE, 8, 8, "保存");
        menu.add(Menu.NONE, 9, 9, "查看已保存的图片");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                showListDialog(1);
                break;
            case 2:
                showSeekBarDialog(2);
                break;
            case 3:
                showSeekBarDialog(3);
                break;
            case 4:
                showListDialog(4);
                break;
            case 5:
                drawingView.back();
                break;
            case 6:
                drawingView.forward();
                break;
            case 7:
                drawingView.clear();
                break;
            case 8:
                save();
                break;
            case 9:
                see();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSeekBarDialog(final int itemId){
        SeekBarDialog dialog = new SeekBarDialog(DrawingviewActivity.this);
        dialog.setProgressListener(new SeekBarDialog.OnProgressListener() {
            @Override
            public void onChange(int progress) {
                if(itemId == 2) {
                    if(drawingView.getType() == DrawingView.ERASER){
                        drawingView.setEraserPaintSize(progress*2);
                    }else {
                        drawingView.setPaintSize(progress*2);
                    }

                }else {
                    drawingView.setPaintAlpha((int) ((10-progress)*25.5));
                    Log.v("透明度", (int) ((10-progress)*25.5) + "");
                    Log.v("progress",progress+"");
                }
            }
        });

    }

    private void showListDialog(final int itemId){
        AlertDialog.Builder builder = new AlertDialog.Builder(DrawingviewActivity.this);
        final String[] colorItems = new String[]{"红色","蓝色","绿色","黑色","黄色","灰色"};
        final String[] typeItems = new String[]{"画笔","橡皮","直线","圆形","椭圆","矩形"};
        String[] items;
        if (itemId == 1){
            items = colorItems;
        }else {
            items = typeItems;
        }
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        if (itemId == 1){
                            drawingView.setPaintColor(Color.RED);
                        }else {
                            drawingView.setType(DrawingView.PATH);
                        }
                        break;
                    case 1:
                        if (itemId == 1){
                            drawingView.setPaintColor(Color.BLUE);
                        }else {
                            drawingView.setType(DrawingView.ERASER);
                        }
                        break;
                    case 2:
                        if (itemId == 1){
                            drawingView.setPaintColor(Color.GREEN);
                        }else {
                            drawingView.setType(DrawingView.STRAIGHT_LINE);
                        }
                        break;
                    case 3:
                        if (itemId == 1){
                            drawingView.setPaintColor(Color.BLACK);
                        }else {
                            drawingView.setType(DrawingView.CIRCLE);

                        }
                        break;
                    case 4:
                        if (itemId == 1){
                            drawingView.setPaintColor(Color.YELLOW);
                        }else {
                            drawingView.setType(DrawingView.OVAL);
                        }
                        break;
                    case 5:
                        if (itemId == 1){
                            drawingView.setPaintColor(Color.GRAY);
                        }else {
                            drawingView.setType(DrawingView.RECTANGLE);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void save(){
        Bitmap bitmap = Bitmap.createBitmap(drawingView.getWidth(), drawingView.getHeight(), Bitmap.Config.ARGB_8888);
        drawingView.draw(new Canvas(bitmap));
//        drawingView.buildDrawingCache();
//        Bitmap bitmap = drawingView.getDrawingCache();

        File rootPath = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            rootPath = Environment.getExternalStorageDirectory();//获取跟目录
        }else {
            Toast.makeText(DrawingviewActivity.this,"没有SDK卡", Toast.LENGTH_SHORT).show();
        }
        File f = new File(rootPath+"/riverlet/");

        if (!f.exists())
        {
            f.mkdirs();
        }
        FileOutputStream fOut = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = df.format(new Date());
        final String path  = f+"/"+fileName+".jpeg";
        try {
            fOut = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            Toast.makeText(DrawingviewActivity.this,"已保存", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(DrawingviewActivity.this);
        builder.setTitle("是否查看图片？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("查看", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
                startActivity(intent);
            }
        });
        builder.create().show();

    }

    private void see(){
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        File rootPath = null;
        if (sdCardExist)
        {
            rootPath = Environment.getExternalStorageDirectory();//获取跟目录
        }else {
            Toast.makeText(DrawingviewActivity.this,"没有SDK卡", Toast.LENGTH_SHORT).show();
        }
        File f = new File(rootPath+"/riverlet/");

        if (!f.exists())
        {
            f.mkdirs();
        }

        File file = new File(f.toString());
        // get the folder list
        File[] array = file.listFiles();


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(array[0]), "image/*");
        startActivity(intent);
    }
}
