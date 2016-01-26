package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2016/1/19 0019.
 * 获取任务参与人请求信息
 */
public class RQGetTaskFriendList extends RQBase{
    private String taskId;
    private String nextId;
    private int itemsCount;
    public RQGetTaskFriendList() {
       super();
    }
    public RQGetTaskFriendList(String taskId, String nextId, int itemsCount) {
        super();
        this.taskId = taskId;
        this.nextId = nextId;
        this.itemsCount = itemsCount;
    }
    public RQGetTaskFriendList(String taskId, String nextId) {
        super();
        this.taskId = taskId;
        this.nextId = nextId;
        this.itemsCount=10;
    }
}
