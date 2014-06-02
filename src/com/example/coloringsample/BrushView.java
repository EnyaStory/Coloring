package com.example.coloringsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;


public class BrushView extends ImageView {

   
    public static final int DEFAULT_RADIUS_M = 8;
    
    public static final int DEFAULT_RADIUS_S = 4;

    private int radius; 


    public BrushView(Context context, AttributeSet attrs) {
        super(context, attrs);
       
        this.radius = DEFAULT_RADIUS_M;
        this.setImageResource(R.drawable.select_brush);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.select_brush);
        this.setMaxWidth(bitmap.getWidth());
        this.setMaxHeight(bitmap.getHeight());
        this.setScaleType(ScaleType.FIT_START);
    }


    public int getRadius() {
        return this.radius;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        if (event.getX() < this.getWidth() / 2) {
         
            this.radius = DEFAULT_RADIUS_S;
            this.setImageResource(R.drawable.select_pencil);
        } else {
            this.radius = DEFAULT_RADIUS_M;
            this.setImageResource(R.drawable.select_brush);
        }
     
        invalidate();
        return true;
    }
}
