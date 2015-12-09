package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 我的任务请求
 */
public class RQMyTask extends RQBase {
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
     * 任务状态（进行中（1）或已过期（3））
     */
    public int taskStatus;

    public RQMyTask() {
        super();
    }

    public RQMyTask(String userId, String nextId, int taskStatus) {
        super();
        this.userId = userId;
        this.nextId = nextId;
        this.taskStatus = taskStatus;
    }

    public RQMyTask(String userId, int itemsCount, String nextId,
                             int taskStatus) {
        super();
        this.userId = userId;
        this.itemsCount = itemsCount;
        this.nextId = nextId;
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "RQMyTask{" +
                "userId='" + userId + '\'' +
                ", itemsCount=" + itemsCount +
                ", nextId='" + nextId + '\'' +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
