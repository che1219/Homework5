package com.example.homework5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.PointerIcon;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class Drawing extends AppCompatActivity implements View.OnClickListener {

    Bitmap iamge;
    ImageView imageView;
    MyCanvas myCanvas;
    TouchListener touchListener;
    Button red,green,blue,undo,done,clear;

    Random rd = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        Intent intent = getIntent();
        iamge = (Bitmap)intent.getParcelableExtra("image");
        imageView = (ImageView)findViewById(R.id.image);
        imageView.setImageBitmap(iamge);
        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        touchListener = new TouchListener(this);
        myCanvas.setOnTouchListener(touchListener);
        red = (Button) findViewById(R.id.red);
        green = (Button) findViewById(R.id.green);
        blue = (Button) findViewById(R.id.blue);
        undo = (Button) findViewById(R.id.undo);
        done = (Button) findViewById(R.id.done);
        clear = (Button) findViewById(R.id.clear);
        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        undo.setOnClickListener(this);
        done.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.red:
                myCanvas.setRed();
                break;
            case R.id.blue:
                myCanvas.setBlue();
                break;
            case R.id.green:
                myCanvas.setGreen();
                break;
            case R.id.undo:
                myCanvas.undo();
                break;
            case R.id.clear:
                myCanvas.clear();
                break;
            case R.id.done:
                myCanvas.done();
                Intent finish = new Intent(this, MainActivity.class);
                startActivity(finish);
                break;
        }
    }


    public void addPath(int id, float x, float y) {
        myCanvas.addPath(id, x, y);
    }

    public void updatePath(int id, float x, float y) {
        myCanvas.updatePath(id, x, y);
    }

//    public void removePath(int id) {
//        myCanvas.removePath(id);
//    }

    public void undoTime(int time) {
        myCanvas.undoTime(time);
    }

    public void finishDraw(){
        myCanvas.finsh();
    }

    public void onDoubleTap(float x,float y) {
        myCanvas.onDoubleTap(x,y);
    }

    public void onLongPress(float x,float y) {
        myCanvas.onLongPress(x,y);
    }
}