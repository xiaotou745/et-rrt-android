/*
 * File Name: DownLoadService.java 
 * History:
 * Created by Administrator on 2015-7-15
 */
package com.user.model.download;

import java.io.File;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.renrentui.app.R;
import com.renrentui.tools.SDCardUtils;


public class DownLoadService extends Service {
    public static final String ACTION_DOWNLOADSERVICE_START = "com.user.model.download.DownLoadService.START";
    public static final String ACTION_DOWNLOADSERVICE_STOP = "com.user.model.download.DownLoadService.STOP";

    public static final String ACTION_EXTRA_URL_KEY = "ACTION_EXTRA_URL_KEY";
    public static final String DOWNLOAD_PATH = "/EdaisongNew.apk";
    public static final String IS_SHOW_NOTIFINATION_KEY = "IS_SHOW_NOTIFINATION_KEY";// 是否显示通知栏

    // 下载信息
    public static final String DOWNLOAD_PROGRESS_VALUE = "DOWNLOAD_PROGRESS_VALUE";// 当前下载进度
    public static final String DOWNLOAD_RESULT_VALUE = "DOWNLOAD_RESULT_VALUE";// 下载结果
    public static final String DOWNLOAD_INSTALL_PATH_KEY = "DOWNLOAD_INSTALL_PATH_KEY";// 安装地址

    public Context mContext;
    // 标题
    private int titleId = 0;
    // 下载状态
    public final static int DOWNLOAD_COMPLETE = 0;// 下载完成
    public final static int DOWNLOAD_FAIL = 1;// 下载失败
    public final static int DONGLOAD_LOADING = 2;// 下载中
    public final static int DONGLOAD_STARTED = 3;// 开始下载

    // 通知管理
    private int notification_id = 0;// notification ID
    private NotificationManager updateNotificationManager = null;// 通知栏管理
    private Notification updateNotification = null;// 通知栏目

    private RemoteViews mViews;// 通知栏view
    /** 应用名称 */
    private String app_name;

    // 通知栏跳转Intent
    private Intent updateIntent = null;
    private PendingIntent updatePendingIntent = null;

    // 接受器
    private DownLoadReciver mDownLoadReceive;
    public static final String ACTION_DOWNLOAD_RECIVER_FILTER = "com.user.model.download.DownLoadService";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 注册监听
        mContext = this;
        app_name = mContext.getResources().getString(R.string.app_name);
        updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mDownLoadReceive = new DownLoadReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_DOWNLOAD_RECIVER_FILTER);
        registerReceiver(mDownLoadReceive, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String strAction = intent.getAction();
            if (ACTION_DOWNLOADSERVICE_START.equals(strAction)) {
                // 开始下载
                // notification
                createNotification("开始下载", 0);
                // startThread
                startDownLoadThread(intent);
            } else if (ACTION_DOWNLOADSERVICE_STOP.equals(strAction)) {
                // 停止
                stopDownLoadService();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消监听
        if (mDownLoadReceive != null) {
            unregisterReceiver(mDownLoadReceive);
            mDownLoadReceive = null;
        }
    }

    /**
     * 停止服务
     */
    private void stopDownLoadService() {
        this.stopSelf();
    }

    // 创建通知栏
    public void createNotification(String title, int mProgress) {
        android.app.Notification.Builder builder = new Notification.Builder(this);
        mViews = new RemoteViews(getPackageName(), R.layout.download_notification);
        mViews.setTextViewText(R.id.tv_notification_title, app_name);
        mViews.setTextViewText(R.id.tv_notification_progress, mProgress + "%");
        mViews.setProgressBar(R.id.pb_notification_progress, 100, mProgress, false);
        builder.setContent(mViews);
        // 设置下载过程中，点击通知栏，回到主界面
        updateIntent = new Intent(Intent.ACTION_MAIN);
        updateIntent.addCategory(Intent.CATEGORY_HOME);
        updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(updatePendingIntent);
        builder.setTicker(title);
        builder.setSmallIcon(R.drawable.logo).setWhen(System.currentTimeMillis()).setAutoCancel(true);// 设置可以清除
        updateNotification = builder.getNotification();
        updateNotification.icon = R.drawable.logo;
        updateNotification.flags = Notification.FLAG_AUTO_CANCEL;// 单击自动清除
        updateNotificationManager.notify(notification_id, updateNotification);// 发出通知
    }

    /**
     * 开启下载线程
     */
    private void startDownLoadThread(Intent data) {
        String pathFileDir = "";
        if (SDCardUtils.isExistSDCard()) {
            pathFileDir = Environment.getExternalStorageDirectory().toString() + "/renrentuiDownLoad";
        } else {
            pathFileDir = Environment.getDownloadCacheDirectory().toString() + "/renrentuiDownLoad";
        }
        String strUrl = data.getStringExtra(ACTION_EXTRA_URL_KEY);
        Thread mDonwnLoadThread = new Thread(new DownloadTask(strUrl, pathFileDir, pathFileDir + DOWNLOAD_PATH, null,
                getApplicationContext())) {
        };
        mDonwnLoadThread.start();
    }

    /**
     * 安装apk
     * 
     * @param file
     */
    private void installApk(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        // ScreenManager.getInstance().popActivity();

        mContext.startActivity(intent);
    }

    // ============================下载信息==================================================
    class DownLoadReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int strProgressValue = intent.getIntExtra(DOWNLOAD_PROGRESS_VALUE, 0);
            int iDownLoadValue = intent.getIntExtra(DOWNLOAD_RESULT_VALUE, DOWNLOAD_FAIL);
            String strInstallPath = intent.getStringExtra(DOWNLOAD_INSTALL_PATH_KEY);
            switch (iDownLoadValue) {
            case DONGLOAD_STARTED:
                Toast.makeText(context, "开始下载", 0).show();
                break;
            case DOWNLOAD_FAIL:
                // 清除notication
                updateNotificationManager.cancel(notification_id);
                Toast.makeText(context, "下载失败", 0).show();
                break;
            case DOWNLOAD_COMPLETE:
                try {
                    updateNotificationManager.cancel(notification_id);
                    File mFile = new File(strInstallPath);
                    installApk(mFile);
                } catch (Exception e) {
                    e.getMessage();
                }
                break;
            case DONGLOAD_LOADING:
                createNotification("下载中", strProgressValue);
                break;
            }
        }
    }

}
