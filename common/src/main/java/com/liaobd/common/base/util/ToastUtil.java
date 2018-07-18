package com.liaobd.common.base.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by liuweiqi on 2018/4/2.
 */

public class ToastUtil {
    private static Toast sToast;

    public static void shortToast(Context ctx, String text) {
        checkToast();
        sToast = Toast.makeText(ctx.getApplicationContext(), text, Toast.LENGTH_SHORT);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }

    public static void shortToast(Context ctx, @StringRes int strId) {
        checkToast();
        sToast = Toast.makeText(ctx.getApplicationContext(), strId, Toast.LENGTH_SHORT);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }

    public static void longToast(Context ctx, String text) {
        checkToast();
        sToast = Toast.makeText(ctx.getApplicationContext(), text, Toast.LENGTH_LONG);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }

    public static void longToast(Context ctx, @StringRes int strId) {
        checkToast();
        sToast = Toast.makeText(ctx.getApplicationContext(), strId, Toast.LENGTH_LONG);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }

    private static void checkToast() {
        if (sToast != null) {
            sToast.cancel();
        }
    }

}
