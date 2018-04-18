package com.vincent.blurdialog.blur;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.vincent.blurdialog.R;

/**
 * Created by vincent on 2017/11/22.
 */

public class RoundRectBlurView extends RealtimeBlurView {
    private Paint mPaint;
    private RectF mRectF;
    private float mXRadius;
    private float mYRadius;

    private Bitmap mRoundBitmap;
    private Canvas mTmpCanvas;

    public RoundRectBlurView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundRectBlurView);
        mXRadius = a.getDimension(R.styleable.RoundRectBlurView_xRadius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics()));
        mYRadius = a.getDimension(R.styleable.RoundRectBlurView_yRadius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics()));
        a.recycle();
    }

    @Override
    protected void drawBlurredBitmap(Canvas canvas, Bitmap blurredBitmap, int overlayColor) {
        super.drawBlurredBitmap(canvas, blurredBitmap, overlayColor);
        // 制造一个和View一样大小的带圆角的bitmap，圆角之外的部分为透明色
        // 此bitmap用来将从父类传进来的canvas裁切为圆角
        mRectF.right = getWidth();
        mRectF.bottom = getHeight();
        if (mRoundBitmap == null) {
            mRoundBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        }
        if (mTmpCanvas == null) {
            mTmpCanvas = new Canvas(mRoundBitmap);
        }
        mTmpCanvas.drawRoundRect(mRectF, mXRadius, mYRadius, mPaint);

        // 对父类传来的canvas进行圆角裁切
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mRoundBitmap, 0 , 0, mPaint);

//        if (blurredBitmap != null) {
//            mRectF.right = getWidth();
//            mRectF.bottom = getHeight();
//
//            mPaint.reset();
//            mPaint.setAntiAlias(true);
//            BitmapShader shader = new BitmapShader(blurredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//            Matrix matrix = new Matrix();
//            matrix.postScale(mRectF.width() / blurredBitmap.getWidth(), mRectF.height() / blurredBitmap.getHeight());
//            shader.setLocalMatrix(matrix);
//            mPaint.setShader(shader);
//            canvas.drawRoundRect(mRectF, mXRadius, mYRadius, mPaint);
//
//            mPaint.reset();
//            mPaint.setAntiAlias(true);
//            mPaint.setColor(overlayColor);
//            canvas.drawRoundRect(mRectF, mXRadius, mYRadius, mPaint);
//        }
    }

    public void setRadius(float radius) {
        this.mXRadius = radius;
        this.mYRadius = radius;
    }
}
