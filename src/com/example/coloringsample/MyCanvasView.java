package com.example.coloringsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class MyCanvasView extends View {

    private Paint paint;
    private Paint bitmapPaint;
    private Path path; 
    private Bitmap bitmap;
    private Bitmap undoBitmap; 
    private Canvas canvas;
    private float xPos, yPos; 
    private PaletteView paletteView;
    private BrushView brushView;
    private static final float TOUCH_TOLERANCE = 4; 


    public MyCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        BlurMaskFilter mastFilter = new BlurMaskFilter(4.0f,
                BlurMaskFilter.Blur.INNER);

        this.path = new Path();

        this.paint = new Paint();
        this.paint.setStrokeWidth(BrushView.DEFAULT_RADIUS_M);
        this.paint.setAntiAlias(true);
        this.paint.setStrokeJoin(Paint.Join.ROUND);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setMaskFilter(mastFilter);

        this.bitmapPaint = new Paint();
    }


    public void setPaletteView(PaletteView view) {
        this.paletteView = view;
    }


    public void setBrushView(BrushView view) {
        this.brushView = view;
    }


    public void setColor(int color) {
        this.paint.setColor(color);
    }


    public void setRadius(int radius) {
        this.paint.setStrokeWidth(radius);
    }


    public Bitmap getMBitmap() {
        return this.bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
       
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        }
        this.paint.setColor(this.paletteView.getColor());
        this.paint.setStrokeWidth(this.brushView.getRadius());
        canvas.drawPath(path, paint);
    }


    private void start(float x, float y) {
        path.reset();
        path.moveTo(x, y);
        xPos = x;
        yPos = y;
    }


    private void move(float x, float y) {
      
        float dx = Math.abs(x - xPos);
        float dy = Math.abs(y - yPos);

     
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(xPos, yPos, (x + xPos) / 2, (y + yPos) / 2);
            xPos = x;
            yPos = y;
        }
    }


    private void end() {
        undoBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        path.lineTo(xPos, yPos);
        canvas.drawPath(path, paint);
        path.reset();
    }


    public void clear() {
        undoBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        path.reset();
        invalidate();
    }


    public void undo() {
        if (undoBitmap != null) {
            bitmap = undoBitmap;
            canvas.setBitmap(bitmap);
            path.reset();
        }
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("palette", "myCanvasView.onTouchEvent");
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            start(x, y);
            invalidate();
            break;
        case MotionEvent.ACTION_MOVE:
            move(x, y);
            invalidate();
            break;
        case MotionEvent.ACTION_UP:
            end();
            invalidate();
            break;
        }
        return true;
    }
}