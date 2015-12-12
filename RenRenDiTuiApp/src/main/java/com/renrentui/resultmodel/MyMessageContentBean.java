package com.renrentui.resultmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 我的资料内容
 */
public class MyMessageContentBean implements Serializable {
  public String   id ;//消息Id
    public String    title;// 消息标题
    public String    msg ;//消息内容
    public String   hasRead ;//消息是否已读
    public String   taskId ;//任务id
    public String time;//时间

    public MyMessageContentBean() {
        super();
    }

    public MyMessageContentBean(String taskId, String id, String title, String msg, String hasRead,String time) {
        super();
        this.time = time;
        this.taskId = taskId;
        this.id = id;
        this.title = title;
        this.msg = msg;
        this.hasRead = hasRead;
    }

    @Override
    public String toString() {
        return "MyMessageContentBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", msg='" + msg + '\'' +
                ", hasRead='" + hasRead + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
