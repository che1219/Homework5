package com.example.homework5;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MyCanvas extends View {
    HashMap <Integer, Path> activePaths;
    int color = 0;

    ArrayList<Path> pathList = new ArrayList<Path>();

    ArrayList<Integer> undoList = new ArrayList<Integer>();
    ArrayList<Integer> colorList = new ArrayList<Integer>();
    ArrayList<Integer> iconList = new ArrayList<Integer>();
    ArrayList<Float> xList = new ArrayList<Float>();
    ArrayList<Float> yList = new ArrayList<Float>();
    Paint pathPaint;
    int undoTimes = 0;

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        activePaths = new HashMap<>();
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.RED);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(70);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Resources res = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(res,R.drawable.star);
//        canvas.drawBitmap(bitmap, 0, 0, pathPaint);
        int count = 0;
        int icount = 0;
        for(int i = 0; i < undoList.size(); i++) {
            if(undoList.get(i) != -1 && undoList.get(i) != -2) {
                switch (colorList.get(i)) {
                    case 0:
                        pathPaint.setColor(Color.RED);
                        break;
                    case 1:
                        pathPaint.setColor(Color.BLUE);
                        break;
                    case 2:
                        pathPaint.setColor(Color.GREEN);
                        break;
                }

                for (int j = 0; j < undoList.get(i); j++) {
                    Path path = pathList.get(count + j);
                    canvas.drawPath(path, pathPaint);
                }
                count = count + undoList.get(i);
            }

        }
        for (int i = 0; i < iconList.size(); i++) {
            if (iconList.get(i) == -1) {
                 Resources res = getResources();
                 Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.star);
                 bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                 canvas.drawBitmap(bitmap, xList.get(i), yList.get(i), pathPaint);
            } else {
                 Resources res = getResources();
                 Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.heart);
                 bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                 canvas.drawBitmap(bitmap, xList.get(i), yList.get(i), pathPaint);
            }
        }
        super.onDraw(canvas);
    }

    public void addPath(int id, float x, float y) {
        Path path = new Path();
        path.moveTo(x, y);
        activePaths.put(id, path);
        pathList.add(path);
        invalidate();
    }

    public void updatePath(int id, float x, float y) {
        Path path = activePaths.get(id);
        if(path != null){
            path.lineTo(x, y);
        }
        invalidate();
    }

    public void finsh(){
        for(Path path: activePaths.values()){
            pathList.add(path);
        }
    }

    public void removePath() {
        undoList.remove(undoList.size()-1);
    }

    public void undoTime(int times){
        undoList.add(times);
        colorList.add(color);
    }

    public void setRed(){
        color = 0;
    }

    public void setBlue(){
        color = 1;
    }

    public void setGreen(){
        color = 2;
    }

    public void clear() {
        undoTimes = 0;
        activePaths = new HashMap<>();
        pathList.clear();
        colorList.clear();
        undoList.clear();
        xList.clear();
        yList.clear();
        iconList.clear();
        invalidate();

    }

    public void undo() {
        if(!undoList.isEmpty()) {
            int undoTimes = undoList.get(undoList.size() - 1);
            for (int i = 0; i < undoTimes; i++) {
                if (!pathList.isEmpty()) {
                    pathList.remove(pathList.size() - 1);
                }
            }
            undoList.remove(undoList.size() - 1);
            colorList.remove(colorList.size() - 1);
        }
        invalidate();
    }

    public void onDoubleTap(float x,float y){
        iconList.add(-1);
        xList.add(x);
        yList.add(y);
        undo();
        invalidate();
    }



    public void onLongPress(float x,float y){
        iconList.add(-2);
        xList.add(x);
        yList.add(y);
        undo();
        invalidate();
    }

    public void done(){
        color = 0;
        undoTimes = 0;
        activePaths = new HashMap<>();
        pathList.clear();
        colorList.clear();
        undoList.clear();
        xList.clear();
        yList.clear();
        iconList.clear();
    }

}
