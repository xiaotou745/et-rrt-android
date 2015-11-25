/*
 * File Name: DownloadTask.java 
 * History:
 * Created by LiBingbing on 2013-9-17
 */
package com.user.model.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class DownloadTask implements Runnable {
    // ==========================================================================
    // Constants
    // ==========================================================================

    // ==========================================================================
    // Fields
    // ==========================================================================
    private String url; // 服务器路径
    private String filepath; // 本地文件路径
    private String fileDir;
    private ProgressDialog pd;
    private static Context mActContext;

    // ==========================================================================
    // Constructors
    // ==========================================================================
    public DownloadTask(String url, String fileDir, String filepath, ProgressDialog prgressDialog, Context con) {
        this.url = url;
        this.filepath = filepath;
        // this.pd = prgressDialog;
        this.fileDir = fileDir;
        mActContext = con;
    }

    // ==========================================================================
    // Getters
    // ==========================================================================

    // ==========================================================================
    // Setters
    // ==========================================================================

    // ==========================================================================
    // Methods
    // ==========================================================================
    public void run() {
        try {
            File file = downLoadFile(url, fileDir, filepath, pd);
            if (file != null) {
                Intent mIntent = new Intent();
                mIntent.setAction(DownLoadService.ACTION_DOWNLOAD_RECIVER_FILTER);
                mIntent.putExtra(DownLoadService.DOWNLOAD_PROGRESS_VALUE, 100);
                mIntent.putExtra(DownLoadService.DOWNLOAD_INSTALL_PATH_KEY, filepath);
                mIntent.putExtra(DownLoadService.DOWNLOAD_RESULT_VALUE, DownLoadService.DOWNLOAD_COMPLETE);
                mActContext.sendBroadcast(mIntent);
            } else {
                Intent mIntent = new Intent();
                mIntent.setAction(DownLoadService.ACTION_DOWNLOAD_RECIVER_FILTER);
                mIntent.putExtra(DownLoadService.DOWNLOAD_RESULT_VALUE, DownLoadService.DOWNLOAD_FAIL);
                mActContext.sendBroadcast(mIntent);
            }
            // pd.dismiss();
            // if (null != file) {
            // installApk(file);
            // } else {
            // // Looper.prepare();
            // // // mActivity.showCenterToast(R.string.download_fail, Toast.LENGTH_SHORT);
            // // Looper.loop();
            // }
        } catch (Exception e) {
            // mActivity.showCenterToast(R.string.download_fail, Toast.LENGTH_SHORT);
            // pd.dismiss();

        }
    }

    /**
     * 
     * @param path
     *            服务器文件路径
     * @param filepath
     *            本地文件路径
     * @return 本地文件对象
     * @throws Exception
     */
    public static File downLoadFile(String path, String fileDir, String filepath, ProgressDialog pd) {
        try {

            int down_step = 5;// 数值阶段点
            int downCount = 0;// 已经下载的百分比
            Intent mIntent = new Intent();
            mIntent.setAction(DownLoadService.ACTION_DOWNLOAD_RECIVER_FILTER);
            long fileSize = getFileSize(path);
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(30000);
            // int total = conn.getContentLength();
            // pd.setMax(total);
            InputStream is = conn.getInputStream();
            File file = new File(filepath);
            File mfileDir = new File(fileDir);
            if (!mfileDir.isDirectory()) {
                mfileDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            int process = 0;
            if (is != null) {
                mIntent.putExtra(DownLoadService.DOWNLOAD_PROGRESS_VALUE, 0);
                mIntent.putExtra(DownLoadService.DOWNLOAD_INSTALL_PATH_KEY, "");
                mIntent.putExtra(DownLoadService.DOWNLOAD_RESULT_VALUE, DownLoadService.DONGLOAD_STARTED);
                mActContext.sendBroadcast(mIntent);
            }
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                process += len;
                // pd.setProgress(process);
                // 每增加5%
                if (downCount == 0 || (process * 100 / fileSize - down_step) >= downCount) {
                    downCount += down_step;
                    mIntent.putExtra(DownLoadService.DOWNLOAD_PROGRESS_VALUE, downCount);
                    mIntent.putExtra(DownLoadService.DOWNLOAD_INSTALL_PATH_KEY, filepath);
                    mIntent.putExtra(DownLoadService.DOWNLOAD_RESULT_VALUE, DownLoadService.DONGLOAD_LOADING);
                    mActContext.sendBroadcast(mIntent);
                }
            }
            fos.flush();
            fos.close();
            is.close();
            // 校验文件完整性
            if (fileSize == process) {
                return file;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    /**
     * 获得文件长度
     */
    private static long getFileSize(String urlStr) {
        int nFileLength = -1;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpConnection.getResponseCode();
            if (responseCode >= 400) {
                // processErrorCode(responseCode);
                return -2; // -2 represent access is error
            }
            String sHeader;
            for (int i = 1;; i++) {
                sHeader = httpConnection.getHeaderFieldKey(i);
                if (sHeader != null) {
                    if (sHeader.equalsIgnoreCase("content-length")) {
                        nFileLength = Integer.parseInt(httpConnection.getHeaderField(sHeader));
                        break;
                    }
                } else
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nFileLength;
    }

    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================
}
