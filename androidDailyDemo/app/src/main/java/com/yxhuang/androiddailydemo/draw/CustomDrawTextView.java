package com.yxhuang.androiddailydemo.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by yxhuang
 * Date: 2021/1/8
 * Description: 自定义绘制 textView
 */
public class CustomDrawTextView extends View {

    private Paint mPaint;

    public CustomDrawTextView(Context context) {
        super(context);
        init(context);
    }

    public CustomDrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomDrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(20f);

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        canvas.save();

        canvas.drawText("text draw", 0, -(fm.ascent + fm.descent) / 2, mPaint);

    }
}
