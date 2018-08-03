package com.liuwq.base.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

public class FileTool {

    @Nullable
    public static File getExternalPublicPhotoDir(String dirName) {
        if (!isExternalStorageMounted()) {
            return null;
        } else if (!isExternalStorageRemaining()) {
            return null;
        }
        //        dir = Environment.getExternalStorageDirectory();
        File dir = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES);
        File file = new File(dir, dirName);
        if (file.isFile()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @Nullable
    public static File getExternalPrivateDownloadDir(Context ctx, @NonNull String dirName) {
        if (!isExternalStorageMounted()) {
            return null;
        } else if (!isExternalStorageRemaining()) {
            return null;
        }
        File dir = ctx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir, dirName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @Nullable
    public static File getExternalPrivateMusicDir(Context ctx, @Nullable String dirName) {
        if (!isExternalStorageMounted()) {
            return null;
        } else if (!isExternalStorageRemaining()) {
            return null;
        }
        File dir = ctx.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(dir, dirName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 优先使用外部存储，如果不可用，则使用内部存储
     *
     * @param dirName
     * @return
     */
    @NonNull
    public static File getDiskCacheDir(Context ctx, String dirName) {
        File cachePath;
        if (isExternalStorageMounted()) {
            cachePath = ctx.getExternalCacheDir();
        } else {
            cachePath = ctx.getCacheDir();
        }

        return new File(cachePath, dirName);
    }

    /**
     * SD卡容量是否还有可用容量 ( 基数为 40MB )
     *
     * @return 作者:fighter <br />
     * 创建时间:2013-4-16<br />
     * 修改时间:<br />
     */
    public static boolean isExternalStorageRemaining() {
        long volume = getExternalStorageSpace();
        long mb = 1024 * 1024 * 40;
        if (volume > mb) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * SD卡是否可用
     *
     * @return 作者:fighter <br />
     * 创建时间:2013-5-6<br />
     * 修改时间:<br />
     */
    public static boolean isExternalStorageMounted() {
        return android.os.Environment.MEDIA_MOUNTED
                .equals(android.os.Environment.getExternalStorageState());
    }

    /**
     * 删除 {@code file} 目录下所有文件或者删除 {@code file} 文件
     */
    public static boolean deleteFile(File file) {
        boolean delSuccess = true;
        try {
            if (file == null) {
                delSuccess = false;
            } else {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    if (files.length == 0) {
                        return delSuccess & file.delete();
                    } else {
                        for (File subFile : files) {
                            return delSuccess & deleteFile(subFile);
                        }
                    }
                } else {
                    return delSuccess & file.delete();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            delSuccess = false;
        } finally {
            return delSuccess;
        }
    }

    /**
     * 获取 {@code file} 目录或文件大小
     *
     * @param file
     * @return in byte.
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file == null) {
            return size;
        }
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size += getFileSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 以时间创建文件名
     *
     * @param prefix
     * @param postfix
     * @return
     */
    public static String createFileName(String prefix, String postfix) {
        String date = FormatUtil.getDate("yyyy-MM-dd-HH-mm-ss");
        if (!TextUtils.isEmpty(prefix)) {
            date = prefix + date;
        }
        if (!TextUtils.isEmpty(postfix)) {
            date += postfix;
        }
        return date;
    }

    /**
     * 文件重命名
     *
     * @param file    要重命名的文件
     * @param newName 新的名字
     * @return 作者:fighter <br />
     * 创建时间:2013-3-4<br />
     * 修改时间:<br />
     */
    public static boolean renameFile(File file, String newName) {
        return file.renameTo(new File(file.getParentFile(), newName));
    }

    /**
     * SD卡可用容量
     *
     * @return 字节数。 -1 SD card 读取空间错误! 作者:fighter <br />
     * 创建时间:2013-3-4<br />
     * 修改时间:<br />
     */
    public static long getExternalStorageSpace() {
        try {
            StatFs statFs = new StatFs(getExternalDirectory());
            return (long) statFs.getBlockSize()
                    * (long) statFs.getAvailableBlocks();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getExternalDirectory() {
        return Environment.getExternalStorageDirectory()
                .getAbsolutePath();
    }

    public static Uri getUriForFile(File file, @NonNull Context context) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context,
                    context.getPackageName() + ".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
