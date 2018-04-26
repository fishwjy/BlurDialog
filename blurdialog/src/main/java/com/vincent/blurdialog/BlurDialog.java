package com.vincent.blurdialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vincent.blurdialog.adapter.SingleSelectAdapter;
import com.vincent.blurdialog.blur.RoundRectBlurView;
import com.vincent.blurdialog.helper.BlurDialogHelper;
import com.vincent.blurdialog.listener.OnItemClick;
import com.vincent.blurdialog.listener.OnNegativeClick;
import com.vincent.blurdialog.listener.OnPositiveClick;
import com.vincent.blurdialog.view.DeterminateProgressView;
import com.vincent.blurdialog.view.IndeterminateProgressView;

import java.util.List;

/**
 * Created by vincent on 2017/11/22.
 */

public class BlurDialog {
    public static final int TYPE_DOUBLE_OPTION = 0;
    public static final int TYPE_SINGLE_OPTION = 1;
    public static final int TYPE_NONE_OPTION = 2;
    public static final int TYPE_DELETE = 3;
    public static final int TYPE_WAIT = 4;
    public static final int TYPE_PROGRESS = 5;
    public static final int TYPE_SINGLE_SELECT = 6;

    private int mWindowPaddingStart = 70;
    private int mWindowPaddingEnd = 70;

    private Dialog mDialog;

    private Builder builder;

    private float preProgress = 0;
    private float curProgress = 0;
    private ObjectAnimator mProgressAnimator;

    public BlurDialog() {
    }

    public BlurDialog(Builder builder) {
        this.builder = builder;
    }

    private BlurDialog createDialog(Context ctx) {
        mDialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        mDialog.setCanceledOnTouchOutside(builder.isOutsideCancelable);
        mDialog.setCancelable(builder.isCancelable);
        if (builder.dismissListener != null) mDialog.setOnDismissListener(builder.dismissListener);

        switch (builder.type) {
            case TYPE_DOUBLE_OPTION:
                confirmDoubleStyle(mDialog);
                break;
            case TYPE_SINGLE_OPTION:
                confirmSingleStyle(mDialog);
                break;
            case TYPE_NONE_OPTION:
                confirmNoneStyle(mDialog);
                break;
            case TYPE_DELETE:
                deleteStyle(mDialog);
                break;
            case TYPE_WAIT:
                waitStyle(mDialog, builder);
                break;
            case TYPE_PROGRESS:
                progressStyle(mDialog, builder);
                break;
            case TYPE_SINGLE_SELECT:
                selectSingleStyle(mDialog);
                break;
        }

        return this;
    }

    private void confirmDoubleStyle(Dialog dialog) {
        dialog.setContentView(R.layout.vw_dialog_option_double);

        TextView vw_dialog_tv_msg = dialog.findViewById(R.id.vw_dialog_tv_msg);
        vw_dialog_tv_msg.setText(builder.message);

        FrameLayout vw_dialog_fl_center = dialog.findViewById(R.id.vw_dialog_fl_center);
        if (builder.centerView != null && builder.centerViewParams != null) {
            vw_dialog_fl_center.setVisibility(View.VISIBLE);
            vw_dialog_fl_center.addView(builder.centerView, builder.centerViewParams);
        } else {
            vw_dialog_fl_center.setVisibility(View.GONE);
        }

        TextView vw_dialog_tv_ok = dialog.findViewById(R.id.vw_dialog_tv_ok);
        if (!TextUtils.isEmpty(builder.positiveMsg)) {
            vw_dialog_tv_ok.setText(builder.positiveMsg);
        }
        vw_dialog_tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.positiveClick != null) {
                    builder.positiveClick.onClick();
                }
            }
        });

        TextView vw_dialog_tv_cancel = dialog.findViewById(R.id.vw_dialog_tv_cancel);
        if (!TextUtils.isEmpty(builder.negativeMsg)) {
            vw_dialog_tv_cancel.setText(builder.negativeMsg);
        }
        vw_dialog_tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.negativeClick != null) {
                    builder.negativeClick.onClick();
                }
            }
        });

        float radius = BlurDialogHelper.getRadius(dialog.getContext(), builder.radius);

        RoundRectBlurView bv = dialog.findViewById(R.id.bv);
        bv.setRadius(radius);

        vw_dialog_tv_ok = dialog.findViewById(R.id.vw_dialog_tv_ok);
        BlurDialogHelper.setBackGroundDrawable(dialog.getContext(), vw_dialog_tv_ok, new float[]{
                0, 0, //左上角
                0, 0, //右上角
                radius, radius, //右下角
                0, 0 //左下角
        });

        vw_dialog_tv_cancel = dialog.findViewById(R.id.vw_dialog_tv_cancel);
        BlurDialogHelper.setBackGroundDrawable(dialog.getContext(), vw_dialog_tv_cancel, new float[]{
                0, 0, //左上角
                0, 0, //右上角
                0, 0, //右下角
                radius, radius //左下角
        });

        centerStyle(dialog);
    }

    private void confirmSingleStyle(Dialog dialog) {
        confirmSingleStyle(dialog, mWindowPaddingStart, mWindowPaddingEnd);
    }

    private void confirmSingleStyle(Dialog dialog, int paddingStart, int paddingEnd) {
        dialog.setContentView(R.layout.vw_dialog_option_single);

        TextView vw_dialog_tv_msg = dialog.findViewById(R.id.vw_dialog_tv_msg);
        vw_dialog_tv_msg.setText(builder.message);

        FrameLayout vw_dialog_fl_center = dialog.findViewById(R.id.vw_dialog_fl_center);
        if (builder.centerView != null && builder.centerViewParams != null) {
            vw_dialog_fl_center.setVisibility(View.VISIBLE);
            vw_dialog_fl_center.addView(builder.centerView, builder.centerViewParams);
        } else {
            vw_dialog_fl_center.setVisibility(View.GONE);
        }

        TextView vw_dialog_tv_ok = dialog.findViewById(R.id.vw_dialog_tv_ok);
        if (!TextUtils.isEmpty(builder.positiveMsg)) {
            vw_dialog_tv_ok.setText(builder.positiveMsg);
        }
        vw_dialog_tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.positiveClick != null) {
                    builder.positiveClick.onClick();
                }
            }
        });

        float radius = BlurDialogHelper.getRadius(dialog.getContext(), builder.radius);

        RoundRectBlurView bv = dialog.findViewById(R.id.bv);
        bv.setRadius(radius);

        vw_dialog_tv_ok = dialog.findViewById(R.id.vw_dialog_tv_ok);
        BlurDialogHelper.setBackGroundDrawable(dialog.getContext(), vw_dialog_tv_ok, new float[]{
                0, 0, //左上角
                0, 0, //右上角
                radius, radius, //右下角
                radius, radius //左下角
        });

        centerStyle(dialog, paddingStart, paddingEnd);
    }

    private void confirmNoneStyle(Dialog dialog) {
        confirmNoneStyle(dialog, mWindowPaddingStart, mWindowPaddingEnd);
    }

    private void confirmNoneStyle(Dialog dialog, int paddingStart, int paddingEnd) {
        dialog.setContentView(R.layout.vw_dialog_option_none);

        TextView vw_dialog_tv_msg = dialog.findViewById(R.id.vw_dialog_tv_msg);
        vw_dialog_tv_msg.setText(builder.message);

        FrameLayout vw_dialog_fl_center = dialog.findViewById(R.id.vw_dialog_fl_center);
        if (builder.centerView != null && builder.centerViewParams != null) {
            vw_dialog_fl_center.setVisibility(View.VISIBLE);
            vw_dialog_fl_center.addView(builder.centerView, builder.centerViewParams);
        } else {
            vw_dialog_fl_center.setVisibility(View.GONE);
        }

        float radius = BlurDialogHelper.getRadius(dialog.getContext(), builder.radius);

        RoundRectBlurView bv = dialog.findViewById(R.id.bv);
        bv.setRadius(radius);

        centerStyle(dialog, paddingStart, paddingEnd);
    }

    private void deleteStyle(Dialog dialog) {
        confirmDoubleStyle(dialog);
        TextView vw_dialog_tv_ok = dialog.findViewById(R.id.vw_dialog_tv_ok);
        vw_dialog_tv_ok.setText(dialog.getContext().getResources().getString(R.string.vw_dialog_delete));
        vw_dialog_tv_ok.setTextColor(dialog.getContext().getResources().getColor(R.color.vw_dialog_red));
    }

    private void waitStyle(Dialog dialog, Builder builder) {
        builder.centerView(new IndeterminateProgressView(dialog.getContext()),
                new FrameLayout.LayoutParams(
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, dialog.getContext().getResources().getDisplayMetrics()),
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, dialog.getContext().getResources().getDisplayMetrics())));

        confirmNoneStyle(dialog, 120, 120);
    }

    private void progressStyle(Dialog dialog, Builder builder) {
        DeterminateProgressView view = new DeterminateProgressView(dialog.getContext());
        builder.centerView(view, new FrameLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, dialog.getContext().getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, dialog.getContext().getResources().getDisplayMetrics())));

        confirmSingleStyle(dialog);
    }

    private void selectSingleStyle(Dialog dialog) {
        dialog.setContentView(R.layout.vw_dialog_select_single);

        TextView vw_dialog_tv_cancel = dialog.findViewById(R.id.vw_dialog_tv_cancel);
        if (!TextUtils.isEmpty(builder.singleListCancelMsg)) {
            vw_dialog_tv_cancel.setText(builder.singleListCancelMsg);
        }
        vw_dialog_tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.negativeClick != null) {
                    builder.negativeClick.onClick();
                }
            }
        });

        float radius = BlurDialogHelper.getRadius(dialog.getContext(), builder.radius);

        RoundRectBlurView bv1 = dialog.findViewById(R.id.bv1);
        bv1.setRadius(radius);

        RoundRectBlurView bv2 = dialog.findViewById(R.id.bv2);
        bv2.setRadius(radius);

        vw_dialog_tv_cancel = dialog.findViewById(R.id.vw_dialog_tv_cancel);
        BlurDialogHelper.setBackGroundDrawable(dialog.getContext(), vw_dialog_tv_cancel, new float[]{
                radius, radius, //左上角
                radius, radius, //右上角
                radius, radius, //右下角
                radius, radius //左下角
        });

        RecyclerView rv_content = dialog.findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        rv_content.setAdapter(new SingleSelectAdapter(builder.singleList, radius, builder.itemClick));

        bottomStyle(dialog);
    }

    private void centerStyle(Dialog dialog) {
        centerStyle(dialog, mWindowPaddingStart, mWindowPaddingEnd);
    }

    private void centerStyle(Dialog dialog, int paddingStart, int paddingEnd) {
        Window window = dialog.getWindow();
        if (window == null) return;
        //设置window背景为透明色，否则会看到圆角外有黑色背景
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        //设置window弹出收起动画
        window.setWindowAnimations(R.style.vw_dialog_anim_center);
        //设置居中
        window.setGravity(Gravity.CENTER);
        //设置内边距
        window.getDecorView().setPadding(
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        paddingStart,
                        dialog.getContext().getResources().getDisplayMetrics()),
                0,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        paddingEnd,
                        dialog.getContext().getResources().getDisplayMetrics()),
                0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
    }

    private void bottomStyle(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window == null) return;
        //设置window背景为透明色，否则会看到圆角外有黑色背景
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        //设置window弹出收起动画
        window.setWindowAnimations(R.style.vw_dialog_anim_bottom);
        //设置居中
        window.setGravity(Gravity.BOTTOM);
        //设置内边距
        window.getDecorView().setPadding(
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        20,
                        dialog.getContext().getResources().getDisplayMetrics()),
                0,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        20,
                        dialog.getContext().getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        10,
                        dialog.getContext().getResources().getDisplayMetrics()));
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        }

        return false;
    }

    public static class Builder {
        private Context ctx;
        private boolean isCancelable;
        private boolean isOutsideCancelable;
        private int type = TYPE_DOUBLE_OPTION;
        private float radius = -1; //dp
        private CharSequence message;
        private CharSequence positiveMsg;
        private CharSequence negativeMsg;
        private List<CharSequence> singleList;
        private CharSequence singleListCancelMsg;
        private View centerView;
        private FrameLayout.LayoutParams centerViewParams;

        private OnPositiveClick positiveClick;
        private OnNegativeClick negativeClick;
        private OnItemClick itemClick;
        private DialogInterface.OnDismissListener dismissListener;
        private OnProgressFinishedListener onProgressFinishedListener;

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder isCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public Builder isOutsideCancelable(boolean cancelable) {
            this.isOutsideCancelable = cancelable;
            return this;
        }

        public Builder radius(float radius) {
            this.radius = radius;
            return this;
        }

        public Builder message(CharSequence msg) {
            this.message = msg;
            return this;
        }

        public Builder singleList(List<CharSequence> singleList) {
            this.singleList = singleList;
            return this;
        }

        public Builder singleListCancelMsg(CharSequence singleListCancelMsg) {
            this.singleListCancelMsg = singleListCancelMsg;
            return this;
        }

        public Builder centerView(View view, FrameLayout.LayoutParams params) {
            this.centerView = view;
            this.centerViewParams = params;
            return this;
        }

        public Builder positiveMsg(CharSequence positiveMsg) {
            this.positiveMsg = positiveMsg;
            return this;
        }

        public Builder negativeMsg(CharSequence negativeMsg) {
            this.negativeMsg = negativeMsg;
            return this;
        }

        public Builder positiveClick(OnPositiveClick positiveClick) {
            this.positiveClick = positiveClick;
            return this;
        }

        public Builder negativeClick(OnNegativeClick negativeClick) {
            this.negativeClick = negativeClick;
            return this;
        }

        public Builder itemClick(OnItemClick itemClick) {
            this.itemClick = itemClick;
            return this;
        }

        public Builder dismissListener(DialogInterface.OnDismissListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }

        public Builder onProgressFinishedListener(OnProgressFinishedListener onProgressFinishedListener) {
            this.onProgressFinishedListener = onProgressFinishedListener;
            return this;
        }

        public BlurDialog build(Context ctx) {
            this.ctx = ctx;
            BlurDialog helper = new BlurDialog(this);
            return helper.createDialog(ctx);
        }

        public Builder createBuilder(Context ctx) {
            this.ctx = ctx;
            return this;
        }
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
        createDialog(builder.ctx);
    }

    public interface OnProgressFinishedListener {
        void onProgressFinished();
    }

    public void setProgress(float percent) {
        //防止频繁重绘，大于10的百分比变动才进行绘制，或者动画正在执行中也不进行绘制
        if (mProgressAnimator != null && mProgressAnimator.isRunning()) return;
        if (percent - preProgress < 10) return;

        if (builder.centerView != null && builder.centerView instanceof DeterminateProgressView
                && mDialog.isShowing() && preProgress < 100) {
            curProgress = percent > 100 ? 100 : percent;
            mProgressAnimator = ObjectAnimator.ofFloat(((DeterminateProgressView) builder.centerView),
                    "progress", preProgress, curProgress);
            mProgressAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    preProgress = curProgress;
                    if (curProgress == 100) {
                        mDialog.dismiss();
                        builder.onProgressFinishedListener.onProgressFinished();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mProgressAnimator.start();
        }
    }
}
