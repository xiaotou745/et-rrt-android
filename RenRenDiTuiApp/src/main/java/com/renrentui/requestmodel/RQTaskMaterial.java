package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2015/12/2 0002.
 * 获取资料请求
 */
public class RQTaskMaterial  extends RQBase {
    /**
     * 用户id
     */
    public String userId;
    /**
     * 为分页处理服务，获取任务的个数。当为0或者不填的时候，默认全部获取
     */
    public int itemsCount = 10;
    /**
     * 为分页处理服务，上页获取到的任务列表中的NextID，不填表示从头开始获取
     */
    public String nextId;
    /**
     * 资料的审核状态(待审核(1);审核通过(2);审核不通过(3))
     */
    public int auditStatus;
    /**
     * 任务id（如果是获取所有资料列表，则传0，如果是某个任务的资料列表，则为任务id）
     */
    public String taskId="0";
    public RQTaskMaterial(){
        super();
    }


    public RQTaskMaterial(String userId, String nextId, int auditStatus) {
        super();
        this.userId = userId;
        this.nextId = nextId;
        this.auditStatus = auditStatus;
    }
    public RQTaskMaterial(String userId, String nextId, int auditStatus, String taskId) {
        super();
        this.userId = userId;
        this.nextId = nextId;
        this.auditStatus = auditStatus;
        this.taskId = taskId;
    }
    public RQTaskMaterial(String userId, int itemsCount, String nextId, int auditStatus, String taskId) {
        super();
        this.userId = userId;
        this.itemsCount = itemsCount;
        this.nextId = nextId;
        this.auditStatus = auditStatus;
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "RQTaskMaterial{" +
                "userId='" + userId + '\'' +
                ", itemsCount=" + itemsCount +
                ", nextId='" + nextId + '\'' +
                ", auditStatus=" + auditStatus +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
