package com.renrentui.requestmodel;

public class RQReceiveTask extends RQBase {
	/** 用户ID */
	public String userId;
	/** 任务ID */
	public String taskId;

	public RQReceiveTask(String userId, String taskId) {
		super();
		this.userId = userId;
		this.taskId = taskId;
	}

	public RQReceiveTask() {
		super();
	}

	@Override
	public String toString() {
		return "RQReceiveTask[userId=" + userId + ",taskId=" + taskId + "]";
	}
}
