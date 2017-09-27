package com.canvasdrawing.CommonFiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.canvasdrawing.R;

import java.util.ArrayList;

public class CanvasView extends View {
    private Context mContext;
    private ArrayList<Draw> mDrawList;
    private ArrayList<Draw> paths = new ArrayList<Draw>();
    private ArrayList<Draw> undonePaths = new ArrayList<Draw>();


    private Paint mPaint;
    private Path mPath;
    private boolean isErase;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private float brushSize = 5;

    int color =Color.parseColor("#5fbdf7");


    public CanvasView(Context context) {
        super(context);
        init(context);
    }

    public CanvasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mDrawList = new ArrayList<Draw>();
        mPath = new Path();
        setupPaint();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        // canvas.drawBitmap(imageBitmap, 0, 0, null);


    }

    private void setupPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setFilterBitmap(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(brushSize);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.black));
        if (isErase) {
            mPaint.setXfermode(new PorterDuffXfermode(
                    PorterDuff.Mode.CLEAR));
            mPaint.setStrokeWidth(50);
        }
    }

    public void setErase(boolean isErase) {
        this.isErase = isErase;
        setupPaint();
    }

    public boolean getErase() {
        return this.isErase;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Draw draw : mDrawList) {
            mPaint.setColor(color);

            canvas.drawPath(draw.path, draw.paint);

        }


    }

    public void setColor(int color) {

        this.color = color;

        setupPaint();
        mPaint.setColor(color);
        invalidate();

        // Log.d(TAG, "Setting paint color to: " + drawPaint.getColor());
    }

    /**
     * Start new Drawing
     */
    public void eraseAll() {
        mDrawList.clear();
        undonePaths.clear();
        paths.clear();

        mPaint.reset();
        setupPaint();
        //    drawCanvas.drawColor(Color.WHITE);
        invalidate();
    }

    public void setBrushSize(float newSize) {
        mPaint = new Paint();
        brushSize = newSize;
        mPaint.setStrokeWidth(brushSize);
        setErase(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Util.mDraw_disable) {
                    touchMove(x, y);}
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }

    private void touchStart(float x, float y) {

        mPath.reset();
        mPath.moveTo(x, y);
        mDrawList.add(new Draw(mPath, mPaint));
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
        mPath = new Path();
    }


    public void onClickUndo() {
        if (mDrawList.size() > 0) {
            undonePaths.add(mDrawList.remove(mDrawList.size() - 1));
            // Util.mShow_alert_dialog=mDrawList.size()!=0?true:false;
            invalidate();
            Log.e("**********","-----size---->"+mDrawList.size());
        }
    }

    public void onClickRedo() {
        if (undonePaths.size() > 0) {
            mDrawList.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        }
    }


    class Draw {
        Path path;
        Paint paint;

        public Draw(Path path, Paint paint) {
            this.paint = paint;
            this.path = path;
        }
    }
}