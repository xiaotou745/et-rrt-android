/*
 * File Name: UploadService.java 
 * History:
 * Created by admin on 2015-4-8
 */
package com.task.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.text.TextUtils;


import com.google.gson.Gson;
import com.renrentui.tools.Constants;
import com.renrentui.util.ApiConstants;
import com.task.upload.bean.UploadPicResultBean;
import com.task.upload.bean.uploadPicBean;
import com.task.upload.db.UploadDB;
import com.task.upload.preference.PreferencesUtils;

public class UploadService extends Service {
    /**
     * 用于发送下载进度的intent filter
     */
    public static final String FILE_UPLOAD_STATE_BROADCAST_INTENT_FILTER = "file_upload_state_broadcast_intent_filter";
    /**
     * 设置上传多线程数量
     */
    public static final String SETTINGS_UPLOAD_NUM = "settings_upload_num";
    /**
     * 下载状态
     */
    public static final int TASK_STATE_UN_TASK = -1; // 开始上传
    public static final int TASK_STATE_START = 0; // 开始上传
    public static final int TASK_STATE_PAUSE = 1; // 暂停
    public static final int TASK_STATE_UPLOADING = 2; // 正在上传中
    public static final int TASK_STATE_COMPLETE = 3; // 完成
    public static final int TASK_STATE_WAITING = 4; // 等待上传
    public static final int TASK_STATE_FAIL = 5; // 上传失败
    public static final int TASK_STATE_STOP = 6; // 上传停止
    public static final int TASK_STATE_REMOVE = 6; // 上传删除

    /**
     * 广播状态
     */
    public static final int ADD_UPLOAD_TASK = 1; // 添加一个上传任务
    public static final int REMOVE_UPLOAD_TASK = 2; // 删除一个上传任务

    /**
     * 广播属性
     */
    public static final String OPERATION = "operation"; // 操作
    public static final String VO = "vo"; // 实体类
    public static final String FILE_LENGTH = "file_length";
    public static final String CUR_LENGTH = "cur_length";

    /**
     * 文件操作类
     */
    private PreferencesUtils preferencesUtil;
    /**
     * 数据库操作类
     */
    private UploadDB db = new UploadDB();
    /**
     * 下载线程池
     */
    private ArrayList<UploadRunnable> threadPool;
    /**
     * 上传任务集合
     */
    private Vector<uploadPicBean> uploadList;
    private Context mSContext;

    private String str_userId;
    private String str_taskId;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferencesUtil = new PreferencesUtils(this);
        mSContext = this;
        db = new UploadDB();
        threadPool = new ArrayList<UploadRunnable>();
        uploadList = new Vector<uploadPicBean>();

        setThreadAmount();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (intent != null) {
            int ops = intent.getIntExtra(OPERATION, -1);
            uploadPicBean vo = (uploadPicBean) intent.getSerializableExtra(VO);
            str_taskId = intent.getStringExtra("TASK_ID");
            str_userId = intent.getStringExtra("USER_ID");
            if (vo != null) {
                switch (ops) {
                case ADD_UPLOAD_TASK:
                    // 添加上传任务
                    startAllUpload(vo);
                    break;
                case REMOVE_UPLOAD_TASK:
                    // 删除上传任务
                    removeUpload(vo);
                    break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        for (UploadRunnable adr : threadPool) {
            if (adr != null) {
                adr.stopRunning();
                adr = null;
            }
        }

        if (db != null) {
            db.close();
            db = null;
        }

        super.onDestroy();
    }

    /**
     * 获取当前网络状态
     * 
     * @return
     */
    private NetworkInfo getCurrentNetStatus() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo();
    }

    static int mid = 0;

    /**
     * 上传数据线程 (Description)
     * 
     * @author admin
     * @version
     */
    private class UploadRunnable implements Runnable {
        private uploadPicBean vo;//上传图片对象
        private boolean flag = true;
        private long fileLength = 0;
        private HttpUploadUtil httpUploadUtil;

        public UploadRunnable() {
            mid++;
        }

        public uploadPicBean getResumeUploadTaskVO() {
            synchronized (uploadList) {
                for (uploadPicBean vo : uploadList) {
                    if (vo.getUploadStatus() == TASK_STATE_WAITING) {// 如果是等待下载状态
                        vo.setUploadStatus(TASK_STATE_UPLOADING);// 修改状态为正在下载
                        return vo;
                    }
                }
                return null;
            }
        }

        @Override
        public void run() {
            while (flag) {
                try {
                    vo = getResumeUploadTaskVO();
                    if (vo != null) {
                        // 若已存在通知信息则删除
                        // cancelNotification();
                        if (TextUtils.isEmpty(vo.getPath())) {
                            // 图片地址为空
                            stopUpload(TASK_STATE_FAIL);
                        } else {
                            File file = new File(vo.getPath());
                            fileLength = file.length();
                            if (fileLength <= 0) {
                                // 文件不存在
                                stopUpload(TASK_STATE_FAIL);
                            } else {
                                startUpload();
                                // 文件存在，开始上传
                                httpUploadUtil = new HttpUploadUtil(ApiConstants.uploadImgApiUrl+"upload/fileupload/uploadimg?uploadFrom=3", mSContext);
                                httpUploadUtil.addTextParameter("uploadFrom ", "3");
                                httpUploadUtil.addFileParameter("imgstream", file);
                                httpUploadUtil.setOnUploadProgressLinstener(new HttpUploadUtil.OnUploadProgressLinstener() {
                                    public void onUploadProgress(long fileLength, long curLength) {
                                        updateUpload(curLength);
                                    }
                                });
                                byte[] b = httpUploadUtil.send();
                                String result = new String(b, "UTF-8");
                                Gson gson = new Gson();
                                UploadPicResultBean obj = gson.fromJson(result, UploadPicResultBean.class);
                                if (obj != null && obj.getStatus()==1 && obj.getData() != null) {
                                    vo.setUploadStatus(TASK_STATE_COMPLETE);
                                    vo.setNetwork_path(obj.getData().getRelativePath());//上传返回的图片地址
                                    vo.setTicket_property(Constants.TYPE_NET);
                                    completeUpload();
                                } else {
                                    // 上传失败
                                    stopUpload(TASK_STATE_FAIL);
//                                    if (obj != null)
//                                        Toast.makeText(UploadService.this, obj.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else {
                        // 如果没有上传任务，停止3秒后轮训
                        Thread.sleep(3 * 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    stopUpload(TASK_STATE_FAIL);
                } finally {
                    // System.out.println("===========Remove Runnable finally");
                    // threadPool.remove(this);
                }
            }
        }

        /**
         * 停止线程
         */
        public void stopRunning() {
            vo.setUploadStatus(TASK_STATE_WAITING);
            flag = false;
            if (httpUploadUtil != null)
                httpUploadUtil.stopUpload();
        }

        /**
         * 更新上传
         */
        private void updateUpload(long curLength) {
           //  System.out.println("==========updateUpload curLength=" + curLength + " fileLength=" + fileLength
            // + " order_id:" + vo.getOrder_id() + " threadId=" + mid);
            vo.setUploadStatus(TASK_STATE_UPLOADING);
            sendBroadcast(TASK_STATE_UPLOADING, vo, fileLength, curLength, "");
        }

        /**
         * 停止上传
         */
        private void stopUpload(int type) {
            if (type == TASK_STATE_FAIL) {
                vo.setUploadStatus(type);// TASK_STATE_WAITING);
                db.delData(UploadService.this, str_userId,str_userId,vo.getTeam_type(),vo.getTeam_num(),vo.getTeam_position());
            } else {
                vo.setUploadStatus(TASK_STATE_STOP);
            }
            sendBroadcast(type, vo, 0, 0, "");
        }

        /**
         * 完成上传
         */
        private void completeUpload() {
            vo.setUploadStatus(TASK_STATE_COMPLETE);
            db.delData(UploadService.this, str_userId,str_userId,vo.getTeam_type(),vo.getTeam_num(),vo.getTeam_position());
            sendBroadcast(TASK_STATE_COMPLETE, vo, 0, 100, "");
        }

        /**
         * 开始上传
         */
        private void startUpload() {
            vo.setUploadStatus(TASK_STATE_START);
            sendBroadcast(TASK_STATE_START, vo, 0, 0, "");
        }
    }

    /**
     * 发送广播
     */
    private void sendBroadcast(int status, uploadPicBean vo, long fileLength, long curLength, String message) {
        Intent intent = new Intent(FILE_UPLOAD_STATE_BROADCAST_INTENT_FILTER);
        intent.putExtra(OPERATION, status);
        intent.putExtra(VO, vo);
        intent.putExtra(FILE_LENGTH, fileLength);
        intent.putExtra(CUR_LENGTH, curLength);
        sendBroadcast(intent);
    }

    /**
     * 开始所有下载
     */
    private void startAllUpload(uploadPicBean vo) {
        setThreadAmount();

        try {
            synchronized (uploadList) {
                // 初始化Globe.DOWNLOADLIST
                if (uploadList == null || uploadList.size() == 0) {
                    uploadList = db.getDataList(this,str_userId,str_taskId,"","","",-1);
                }
                // 如果当前软件不在下载列表中
                if (!uploadList.contains(vo)) {
                    // 设置状态
                    vo.setUploadStatus(TASK_STATE_WAITING);
                    // 加入队列
                    uploadList.add(vo);
                    // 保存下载任务
                    db.addData(this, vo);
                } else {// 在下载列表中
                    synchronized (uploadList) {
                        for (uploadPicBean tmpVO : uploadList) {
                            if (vo.equals(tmpVO)) {
                                if (tmpVO.getUploadStatus() != TASK_STATE_UPLOADING
                                        && tmpVO.getUploadStatus() != TASK_STATE_WAITING)
                                    tmpVO.setUploadStatus(TASK_STATE_WAITING);// 修改状态为等待下载
                            }
                        }
                    }
                }
            }
            vo.setUploadStatus(TASK_STATE_WAITING);
            sendBroadcast(TASK_STATE_WAITING, vo, 0, 0, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUpload(uploadPicBean vo) {
        synchronized (uploadList) {
            if (uploadList != null) {
                for (uploadPicBean tmpVO : uploadList) {
                    if (vo.equals(tmpVO)) {
                        tmpVO.setUploadStatus(TASK_STATE_REMOVE);
                        uploadList.remove(tmpVO);
                        break;
                    }
                }
            }
        }
        db.delData(UploadService.this, str_userId,str_userId,vo.getTeam_type(),vo.getTeam_num(),vo.getTeam_position());
    }

    /**
     * 设置下载线程数量
     */
    public void setThreadAmount() {
        int threadAmount = preferencesUtil.getInt(SETTINGS_UPLOAD_NUM, 1);
        // 设置的线程数量
        if (threadPool == null) {
            threadPool = new ArrayList<UploadRunnable>();
            int i = 0;
            for (; i < threadAmount; i++) {
                UploadRunnable adr = new UploadRunnable();
                threadPool.add(adr);
                Thread thread = new Thread(adr);
                thread.start();
            }
        } else {
            if (threadPool.size() > threadAmount) {// 如果大于指定的线程数，则停掉多余的线程并从集合中移除
                Iterator<UploadRunnable> ite = threadPool.iterator();
                int pos = 0;
                while (ite != null && ite.hasNext()) {
                    UploadRunnable adr = ite.next();
                    if (pos >= threadAmount) {
                        if (adr != null)
                            adr.stopRunning();// 停止下载线程
                        ite.remove();// 从线程池中移除
                        adr = null;
                    }

                    pos++;
                }
            } else {// 小于等于
                int addAmount = threadAmount - threadPool.size();
                if (addAmount > 0) {
                    int i = 0;
                    for (; i < addAmount; i++) {
                        UploadRunnable adr = new UploadRunnable();
                        threadPool.add(adr);
                        Thread thread = new Thread(adr);
                        thread.start();
                    }
                }
            }

        }
    }
}
