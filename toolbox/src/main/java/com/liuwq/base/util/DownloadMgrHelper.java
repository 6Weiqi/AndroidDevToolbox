package com.liuwq.base.util;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * {@link DownloadManager} 帮助类
 * Created by liuwq on 2018/6/1.
 */
public class DownloadMgrHelper {

    /**
     * @param ctx
     * @param downloadId
     * @return -1 if {@code downloadId} not found.
     */
    public static int getDownloadStatusBy(Context ctx, long downloadId) {
        DownloadManager downloadManager = getDownloadMgr(ctx);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
//                .setFilterByStatus(~DownloadManager.STATUS_FAILED);
//                .setFilterByStatus(DownloadManager.STATUS_PENDING | DownloadManager.STATUS_PAUSED
//                        | DownloadManager.STATUS_RUNNING | DownloadManager.STATUS_SUCCESSFUL);
        Cursor cursor = downloadManager.query(query);
        int downloadStatus = -1;
        if (cursor == null) {
            return downloadStatus;
        }
        if (cursor.moveToFirst()) {
            int downloadStatusIdx = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            downloadStatus = cursor.getInt(downloadStatusIdx);
//            int reasonIdx = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
//            int reason = cursor.getInt(reasonIdx);
//            String txt = null;
//            switch (reason) {
//                case DownloadManager.PAUSED_WAITING_TO_RETRY:
//                    break;
//                case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
//                    break;
//                case DownloadManager.PAUSED_UNKNOWN:
//                    break;
//                case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
//                    break;
//            }
        }
        cursor.close();
        return downloadStatus;
    }

    /**
     * @param ctx
     * @param downloadUrl
     * @param downloadSubPath
     * @param visibleInDownloadsUi
     * @param allowScanningByMediaScanner
     * @param notificationVisibility      {@link android.app.DownloadManager.Request#setNotificationVisibility(int)}
     * @param notificationTitle
     * @param notificationDesc
     * @return
     */
    public static long startDownload(Context ctx, String downloadUrl, String downloadSubPath,
                                     boolean visibleInDownloadsUi,
                                     boolean allowScanningByMediaScanner,
                                     int notificationVisibility,
                                     CharSequence notificationTitle,
                                     CharSequence notificationDesc) {
        Uri uri = Uri.parse(downloadUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadSubPath)
                .setVisibleInDownloadsUi(visibleInDownloadsUi);
        if (allowScanningByMediaScanner) {
            request.allowScanningByMediaScanner();
        }
        // 设置通知文本
        request.setNotificationVisibility(notificationVisibility)
                .setTitle(notificationTitle)
                .setDescription(notificationDesc);
        DownloadManager downloadManager = getDownloadMgr(ctx);
        return downloadManager.enqueue(request);
    }

    public static Uri getDownloadedFileUri(Context ctx, long downloadId) {
        return getDownloadMgr(ctx).getUriForDownloadedFile(downloadId);
    }

    /**
     * @param ctx
     * @param downloadIds
     * @return {@link DownloadManager#remove(long...)}
     */
    public static int deleteDownloads(Context ctx, long... downloadIds) {
        return getDownloadMgr(ctx).remove(downloadIds);
    }

    public static DownloadManager getDownloadMgr(Context ctx) {
        return (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
    }
}
