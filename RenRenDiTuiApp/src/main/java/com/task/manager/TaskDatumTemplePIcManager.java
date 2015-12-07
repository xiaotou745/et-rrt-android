package com.task.manager;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.renrentui.app.R;
import com.renrentui.util.Constants;
import com.task.upload.UploadService;
import com.task.upload.bean.uploadPicBean;
import com.task.upload.db.UploadDB;
import com.task.upload.interfaces.TaskTempleUploadPicInterface;

/**
 * Created by Administrator on 2015/12/7 0007.
 * 任务资料模板图片管理类
 */
public class TaskDatumTemplePIcManager {

    // ==========================================================================
    public Context appContext;
    public Context actContext;
    public Activity mAct;

    // 图片管理类
    public PhotoManager photoMgr;
    private UploadDB db = new UploadDB();
    /**
     * 加载提示框
     */
    private ProgressDialog progressDialog = null;

    // =======================
    private String taskId;// 任务id
    private String userId;// 用户id

    private TaskTempleUploadPicInterface TaskTempleUploadPicInterface;
    private uploadPicBean mCurrentUploadVO;
    /**
     * 接收图片上传进度服务
     */
    private MyReceiver receiver = null;

    // Constants
    // ==========================================================================
    public TaskDatumTemplePIcManager(Context actCon,String user_id,String task_id) {
        appContext = actCon;
        actContext = actCon;
        this.userId = user_id;
        this.taskId = task_id;
        init();
    }

    public TaskDatumTemplePIcManager(Context appCon, Context actCon, Activity act,
                                     TaskTempleUploadPicInterface taskInterface,String user_id,String task_id) {
        appContext = appCon;
        mAct = act;
        actContext = act;
        TaskTempleUploadPicInterface = taskInterface;
        this.userId = user_id;
        this.taskId = task_id;
        init();
    }

    // ==========================================================================
    // Fields
    // ==========================================================================

    // ==========================================================================
    // Constructors
    // ==========================================================================

    // ==========================================================================
    // Getters
    // ==========================================================================

    // ==========================================================================
    // Setters
    // ==========================================================================

    // ==========================================================================
    // Methods
    // ==========================================================================
    private void init() {
        photoMgr = new PhotoManager(mAct);
        // 注册接受广播
        receiver = new MyReceiver();
        mAct.registerReceiver(receiver, new IntentFilter(UploadService.FILE_UPLOAD_STATE_BROADCAST_INTENT_FILTER));
    }

    public void unRegisterReceiver() {
        if (receiver != null) {
            mAct.unregisterReceiver(receiver);
        }
    }

    /**
     * 展示拍照选择对话框
     *
     * @param fileName
     */
    public void showCameraDialog(String fileName, uploadPicBean bean, String uId) {
        // if (mCurrentUploadVO != null) {
        // mCurrentUploadVO.
        // }
        mCurrentUploadVO = bean;
        photoMgr.showGetPhotoDialog(mAct, fileName);
        userId = uId;

    }

    /**
     * 拍照回调
     * @param act
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void showOnActivityResult(Activity act, int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        try {
            Bitmap bitmap = photoMgr.onActivityResult(act, requestCode, resultCode, data);
            if (!photoMgr.isImageType(photoMgr.getFilePath())) {
                Toast.makeText(actContext, R.string.add_order_img_type_error, Toast.LENGTH_SHORT).show();
                return;
            }
            mCurrentUploadVO.setPath(photoMgr.getFilePath());
            mCurrentUploadVO.setTicket_property(Constants.TYPE_LOCAL);
            mCurrentUploadVO.setUploadStatus(UploadService.TASK_STATE_START);
            Intent intent = new Intent(mAct, UploadService.class);
            intent.putExtra(UploadService.OPERATION, UploadService.ADD_UPLOAD_TASK);
            intent.putExtra(UploadService.VO, mCurrentUploadVO);
            mAct.startService(intent);
            mCurrentUploadVO.setIcon(bitmap);
            TaskTempleUploadPicInterface.setUploadPicProgress(0,0,"",UploadService.TASK_STATE_START,mCurrentUploadVO);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(actContext, R.string.get_image_by_camera_or_pic_error, Toast.LENGTH_SHORT).show();
        }

    }

    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                uploadPicBean vo = (uploadPicBean) intent.getSerializableExtra(UploadService.VO);
                long fileLength = intent.getLongExtra(UploadService.FILE_LENGTH, 0);
                long curLength = intent.getLongExtra(UploadService.CUR_LENGTH, 0);
                int status = intent.getIntExtra(UploadService.OPERATION,0);

                TaskTempleUploadPicInterface.setUploadPicProgress(fileLength, curLength, "",status, vo);
            }
        }
    }
}