package com.task.upload.bean;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.task.manager.PhotoManager;

import java.io.Serializable;
import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2015/12/7 0007.
 * 图片上传类的bean
 */
public class uploadPicBean   implements Serializable {
    private String user_id;//用户id
    private String task_id;//任务id
    private String tag;//标签
    private String team_type;//任务类型
    private String team_num;//任务顺序号
    private String team_position;//任务位置
    private int uploadStatus  ;//任务状态
    private String path;//路径
    private String network_path;//服务器图片地址
    private int ticket_property;//图片的信息(1:本地  2：网络 3：相机)
    private transient SoftReference<Bitmap> tempIcon;// 序列化忽略此字段
    private String controlKey;//控件key

    public uploadPicBean() {
    }
    public Bitmap getIcon() {
        if ((tempIcon == null || tempIcon.get() == null) && !TextUtils.isEmpty(path)) {
            Bitmap b = PhotoManager.extractThumbNail(path, 200, 200, false);
            setIcon(b);
        }
        return tempIcon == null ? null : tempIcon.get();
    }

    public void releaseIcon() {
        if (tempIcon != null && tempIcon.get() != null && !tempIcon.get().isRecycled()) {
            tempIcon.get().recycle();
        }
        tempIcon = null;
    }

    public String getControlKey() {
        return controlKey;
    }

    public void setControlKey(String controlKey) {
        this.controlKey = controlKey;
    }

    public SoftReference<Bitmap> getTempIcon() {
        return tempIcon;
    }
    public void setTempIcon(SoftReference<Bitmap> tempIcon) {
        this.tempIcon = tempIcon;
    }
    public void setIcon(Bitmap icon) {
        this.tempIcon = new SoftReference<Bitmap>(icon);
    }
    public String getNetwork_path() {
        return network_path;
    }

    public void setNetwork_path(String network_path) {
        this.network_path = network_path;
    }

    public void setTicket_property(int ticket_property) {
        this.ticket_property = ticket_property;
    }

    public int getTicket_property() {
        return ticket_property;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }





    public void setPath(String path) {
        this.path = path;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public String getTag() {
        return tag;
    }

    public String getTeam_type() {
        return team_type;
    }

    public String getTeam_num() {
        return team_num;
    }

    public String getTeam_position() {
        return team_position;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public void setTeam_type(String team_type) {
        this.team_type = team_type;
    }

    public void setTeam_num(String team_num) {
        this.team_num = team_num;
    }

    public void setTeam_position(String team_position) {
        this.team_position = team_position;
    }

    public String getPath() {
        return path;
    }
}
