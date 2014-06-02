package com.example.coloringsample;

import java.util.ArrayList;
import java.util.Iterator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class PaletteView extends View {

    private int color;
    private Paint paint;
    private ArrayList<Integer> colors = new ArrayList<Integer>();


    public PaletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        setColors();

        setColor(Color.BLACK);
    }


    @SuppressLint("DrawAllocation")
	@Override
    protected void onDraw(Canvas canvas) {
        Iterator<Integer> ite = colors.iterator();
        int x = 0;
        int y = 0;
     
        int eachWidth = (int) Math.floor(this.getWidth() / colors.size());

   
        while (ite.hasNext()) {
            int iColor = (Integer) ite.next().intValue();
            paint.setColor(iColor);
            paint.setStyle(Paint.Style.FILL);

       
            Rect r = new Rect();
            int nextX = x + eachWidth;
            int nextY = y + this.getHeight();
            r.set(x, y, nextX, nextY);
            x = nextX;
            canvas.drawRect(r, paint);

            if (color == iColor) {
            
                Rect markR = new Rect(r.left, r.top + (this.getHeight() / 2),
                        r.right, r.bottom);
                GradientDrawable gradientDrawable = new GradientDrawable(
                        Orientation.LEFT_RIGHT, new int[] { Color.WHITE,
                                iColor, Color.BLACK });
                gradientDrawable.setBounds(markR);
                gradientDrawable.setShape(GradientDrawable.OVAL);
                gradientDrawable.draw(canvas);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
     
        float x = event.getX();

   
        int eachWidth = (int) Math.floor(this.getWidth() / colors.size());
        int pos = (int) (x / eachWidth);
        if (pos < colors.size()) {
            Integer color = colors.get(pos);
            setColor(color);
            invalidate();
        }
        return true;
    }

  
    @SuppressLint("UseValueOf")
	protected void setColors() {
        colors.add(new Integer(Color.WHITE));
        colors.add(new Integer(Color.BLACK));
        colors.add(new Integer(Color.GRAY));
        colors.add(new Integer(Color.rgb(122, 64, 0)));
        colors.add(new Integer(Color.RED));
        colors.add(new Integer(Color.rgb(248, 158, 186)));
        colors.add(new Integer(Color.rgb(254, 198, 173)));
        colors.add(new Integer(Color.rgb(254, 147, 33))); 
        colors.add(new Integer(Color.YELLOW));
        colors.add(new Integer(Color.rgb(161, 210, 118))); 
        colors.add(new Integer(Color.GREEN));
        colors.add(new Integer(Color.rgb(0, 133, 60)));
        colors.add(new Integer(Color.CYAN));
        colors.add(new Integer(Color.BLUE));
        colors.add(new Integer(Color.rgb(2, 62, 132)));
        colors.add(new Integer(Color.rgb(120, 65, 156)));
    }


    public int getColor() {
        return this.color;
    }


    private void setColor(int color) {
        this.color = color;
    }
}
