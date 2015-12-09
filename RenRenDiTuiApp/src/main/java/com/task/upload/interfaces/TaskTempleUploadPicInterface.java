package com.task.upload.interfaces;

/**
 * Created by Administrator on 2015/12/7 0007.
 * 任务模板上传图片进度接口
 */
public interface TaskTempleUploadPicInterface {
    void setUploadPicProgress(long fileLength, long curLength, String path, int status, Object objData);// 上传进度

}
