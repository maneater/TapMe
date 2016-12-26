package com.maneater.maneater.tapme.game.color;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ColorView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private Path mPath = null;

    public ColorView(Context context) {
        this(context, null);
    }

    public ColorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mColor = 0;
    private Sharp mSharp = Sharp.Circle;

    public void setColor(int mColor) {
        this.mColor = mColor;
        mPaint.setColor(mColor);
        invalidate();
    }

    public void setSharp(Sharp mSharp) {
        this.mSharp = mSharp;
        invalidate();
    }

    public enum Sharp {
        Circle, Square, Star, Rectangle, Trapezoid, Triangle
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mSharp) {
            case Circle:
                drawCircle(canvas);
                break;
            case Square:
                drawSquare(canvas);
                break;
            case Star:
                drawStar(canvas);
                break;
            case Rectangle:
                drawRectangle(canvas);
                break;
            case Triangle:
                drawTriangle(canvas);
                break;
            case Trapezoid:
                drawTrapezoid(canvas);
                break;
        }
    }

    private void drawTrapezoid(Canvas canvas) {

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mSharp == Sharp.Triangle) {
            Path path = preparePath();
            path.moveTo(w / 2, 0);
            path.lineTo(0, h);
            path.lineTo(w, h);
            path.close();
        } else if (mSharp == Sharp.Star) {
            Path path = preparePath();
            int radius = Math.min(w, h);
            float angle = (float) (4 * Math.PI / 5);

            for (int i = 0; i < 5; i++) {
                float x = (float) (Math.cos((i / 2 * angle - Math.PI / 2)) * radius);
                float y = (float) (Math.sin(i / 2 * angle - Math.PI / 2) * radius);
                if (i != 0) {
                    path.lineTo(x, y);
                } else {
                    path.moveTo(x, y);
                }
            }

        } else if (mSharp == Sharp.Trapezoid) {


        } else {
            mPath = null;
        }
    }

    //    CGFloat radius = 130;
//
//    CGFloat angle = 4 * M_PI / 5;
//
//    CGPoint center=CGPointMake(160, 240);
//
//    NSMutableArray *arrayM = [NSMutableArray arrayWithCapacity:5];
//
//    for (NSInteger i = 0; i < 5; i++) {
//
//// cosf()返回参数的余弦值
//
//        CGFloat x = cosf(i/2 * angle - M_PI_2) * radius;
//
//// sinf()返回参数的正弦值
//
//        CGFloat y = sinf(i/2 * angle - M_PI_2) * radius;
//
//        CGPoint point = CGPointMake(x+ center.x, y+ center.y);
//
//        [arrayM addObject:[NSValue valueWithCGPoint:point]];
//
//    }

    private Path preparePath() {
        if (mPath != null) {
            mPath.reset();
        } else {
            mPath = new Path();
        }
        return mPath;
    }

    private void drawTriangle(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }


    private void drawRectangle(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        if (w == h) {
            canvas.drawRect(0, h * 0.25f, w, h * 0.75f, mPaint);
        } else {
            canvas.drawRect(0, 0, w, h, mPaint);
        }

    }

    private void drawStar(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }


    private void drawSquare(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        if (w == h) {
            canvas.drawRect(0, 0, w, h, mPaint);
        } else if (w > h) {
            canvas.drawRect((w - h) / 2, 0, w - ((w - h) / 2), h, mPaint);
        } else {
            canvas.drawRect(0, (h - w) / 2, w, h - ((h - w) / 2), mPaint);
        }
    }

    private void drawCircle(Canvas canvas) {
        int w = canvas.getWidth() / 2;
        int h = canvas.getHeight() / 2;
        canvas.drawCircle(w, h, Math.min(w, h), mPaint);
    }
}
