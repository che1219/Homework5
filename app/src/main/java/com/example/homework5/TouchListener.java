package com.example.homework5;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;


public class TouchListener implements View.OnTouchListener {
    Drawing drawing;
    GestureDetectorCompat gestureDetectorCompat;
    int undoNumber = 0;

    public TouchListener(Drawing ma) {
        this.drawing = ma;
        gestureDetectorCompat = new GestureDetectorCompat(this.drawing, new MyGestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetectorCompat.onTouchEvent(motionEvent);
        int maskedAction = motionEvent.getActionMasked();
        int id;
        switch(maskedAction){
            case MotionEvent.ACTION_DOWN:
//                    id = motionEvent.getPointerId(0);
//                    drawing.addPath(id, motionEvent.getX(0), motionEvent.getY(0));
//                    undoNumber = 1;
//                drawing.undoTime(undoNumber);
//                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                undoNumber = 0;
                for(int i= 0, size = motionEvent.getPointerCount(); i< size; i++){
                    id = motionEvent.getPointerId(i);
                    drawing.addPath(id, motionEvent.getX(i), motionEvent.getY(i));
                    undoNumber++;
                }
                drawing.undoTime(undoNumber);
                break;
            case MotionEvent.ACTION_MOVE:
                for(int i= 0, size = motionEvent.getPointerCount(); i< size; i++){
                    id = motionEvent.getPointerId(i);
                    drawing.updatePath(id, motionEvent.getX(i), motionEvent.getY(i));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
//                for(int i= 0, size = motionEvent.getPointerCount(); i< size; i++){
//                    int id = motionEvent.getPointerId(i);
//                    drawing.removePath(id);
//                }
//                break;
        }
        return true;
    }



    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            drawing.onDoubleTap(e.getX(),e.getY());
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            drawing.onLongPress(e.getX(),e.getY());
            super.onLongPress(e);
        }
    }
}
