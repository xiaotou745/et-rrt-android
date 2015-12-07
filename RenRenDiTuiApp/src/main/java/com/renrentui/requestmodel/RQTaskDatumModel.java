package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 材料模板请求mode
 */
public class RQTaskDatumModel extends RQBase {
    public String userId;// 用户ID 否
    public String taskId;// 任务ID 否
    public String  taskDatumId;// 资料ID（获取资料详情时必传） 否

    public RQTaskDatumModel() {
        super();
    }

    public RQTaskDatumModel(String userId, String taskId, String taskDatumId) {
        super();
        this.userId = userId;
        this.taskId = taskId;
        this.taskDatumId = taskDatumId;
    }
    public RQTaskDatumModel(String userId, String taskId) {
        super();
        this.userId = userId;
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "RQTaskDatumModel{" +
                "userId='" + userId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskDatumId='" + taskDatumId + '\'' +
                '}';
    }
}
