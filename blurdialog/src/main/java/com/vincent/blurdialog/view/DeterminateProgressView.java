package com.vincent.blurdialog.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.vincent.blurdialog.BlurDialog;

/**
 * Created by Vincent Woo
 * Date: 2018/4/23
 * Time: 17:41
 */

public class DeterminateProgressView extends View {
    private static final float STEP = 360f / 100f;

    private int mViewHeight, mViewWidth;
    private int mCenterX, mCenterY;
    private float mStrokeWidth = 2;
    private Paint mBasicPaint;
    private Paint mBasicCirclePaint;
    private RectF rectF;
    private PorterDuffXfermode xfermode;

    private float progress = 0;

    public DeterminateProgressView(Context context) {
        super(context);
    }

    public DeterminateProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DeterminateProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DeterminateProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mCenterX = mViewWidth / 2;
        mCenterY = mViewHeight / 2;

        init();
    }

    private void init() {
        mBasicPaint = new Paint();
        mBasicPaint.setAntiAlias(true);
        mBasicPaint.setDither(true);//防抖动
        mBasicPaint.setFilterBitmap(true);//如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示速度，本设置项依赖于dither和xfermode的设置
        mBasicPaint.setColor(Color.parseColor("#FF47494D"));

        mBasicCirclePaint = new Paint();
        mBasicCirclePaint.setAntiAlias(true);
        mBasicCirclePaint.setDither(true);
        mBasicCirclePaint.setFilterBitmap(true);
        mBasicCirclePaint.setColor(Color.parseColor("#FF47494D"));
        mBasicCirclePaint.setStyle(Paint.Style.STROKE);
        mBasicCirclePaint.setStrokeWidth(mStrokeWidth);

        // 加减1是为了不出现残影，适当将矩形扩大一点
        rectF = new RectF(mStrokeWidth * 3 - 1, mStrokeWidth * 3 - 1,
                mViewWidth - mStrokeWidth * 3 + 1, mViewHeight - mStrokeWidth * 3 + 1);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        //画外层的黑圈
        canvas.drawCircle(mCenterX, mCenterY, mCenterX - mStrokeWidth, mBasicCirclePaint);
        //画内层的黑圆
        canvas.drawCircle(mCenterX, mCenterY, mCenterX - mStrokeWidth * 3, mBasicPaint);
        //画扇形
        mBasicPaint.setXfermode(xfermode);
        canvas.drawArc(rectF, -90, progress * STEP, true, mBasicPaint);
        mBasicPaint.setXfermode(null);

        canvas.restoreToCount(saved);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
