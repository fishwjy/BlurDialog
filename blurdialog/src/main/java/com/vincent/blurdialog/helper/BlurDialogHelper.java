package com.vincent.blurdialog.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

import com.vincent.blurdialog.R;

/**
 * Created by vincent on 2017/11/22.
 */

public class BlurDialogHelper {
    public static void setBackGroundDrawable(Context ctx, View v, float[] radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(BlurDialogHelper.getClickBgDrawable(ctx, radius));
        } else {
            v.setBackgroundDrawable(BlurDialogHelper.getClickBgDrawable(ctx, radius));
        }
    }

    public static StateListDrawable getClickBgDrawable(Context ctx, float[] radius) {
        //获取颜色
        GradientDrawable selected = new GradientDrawable();
        selected.setColor(ctx.getResources().getColor(R.color.vw_dialog_bg_shadow));
        selected.setCornerRadii(radius);
        ColorDrawable unSelected = new ColorDrawable(Color.TRANSPARENT);

        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed},
                selected);
        drawable.addState(new int[]{-android.R.attr.state_pressed},
                unSelected);

        return drawable;
    }

    public static float getRadius(Context ctx, float builderRadius) {
        if (builderRadius >= 0) {
            return TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    builderRadius,
                    ctx.getResources().getDisplayMetrics());
        } else {
            return ctx.getResources().getDimension(R.dimen.vw_dialog_default_radius);
        }
    }
}
