package com.liuwq.base.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import java.io.File;

/**
 * 应用常用信息获取类
 * Created by android2 on 2018/3/21.
 */

public class AppInfoUtil {

    public static int getVersionCode(Context ctx) {
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
                    0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * @param ctx
     * @param apkFilePath
     * @return {@code null} if {@code apkFilePath} could not be parsed.
     */
    @Nullable
    public static PackageInfo getPackageInfo(Context ctx, @NonNull String apkFilePath) {
        PackageManager pkgMgr = ctx.getPackageManager();
        PackageInfo pkgInfo = pkgMgr.getPackageArchiveInfo(apkFilePath,
                PackageManager.GET_ACTIVITIES);
//        if (pkgInfo != null) {
//            ApplicationInfo appInfo = pkgInfo.applicationInfo;
//            /* 必须加这两句，不然下面 icon 获取是 default icon 而不是应用包的 icon */
//            appInfo.sourceDir = apkFilePath;
//            appInfo.publicSourceDir = apkFilePath;
//            String appName = pkgMgr.getApplicationLabel(appInfo).toString();// 得到应用名
//            String packageName = appInfo.packageName; // 得到包名
//            String versionName = pkgInfo.versionName; // 得到版本信息
//            /* icon1 和 icon2 其实是一样的 */
//            Drawable icon1 = pkgMgr.getApplicationIcon(appInfo);// 得到图标信息
//            Drawable icon2 = appInfo.loadIcon(pkgMgr);
//        }
        return pkgInfo;
    }

    public static CharSequence getAppName(Context ctx) {
        CharSequence appName;
        PackageManager packManager = ctx.getPackageManager();
        ApplicationInfo appInfo = ctx.getApplicationInfo();
        appName = packManager.getApplicationLabel(appInfo);
        return appName;
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String appVersion;
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            appVersion = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            appVersion = "";
        }
        return appVersion;
    }

    public String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public String getSystemModel() {
        return Build.MODEL;
    }

    public String getScreenResolution(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        String screenResolution = metrics.widthPixels + "*" + metrics.heightPixels;
        return screenResolution;
    }

    public static String getPhoneRom(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        // Byte转换为KB或者MB，内存大小规格化
        String rom = Formatter.formatFileSize(context, totalBlocks * blockSize);
        return rom;
    }

//    /**
//     * 获取电话号码
//     */
//    public static String getNativePhoneNumber(Context context) {
//        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(),
//                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(context.getApplicationContext(),
//                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return null;
//        }
//        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
//                .getLine1Number();
//    }
}
