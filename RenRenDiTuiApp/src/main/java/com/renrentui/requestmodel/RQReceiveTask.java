package com.renrentui.requestmodel;

/**
 * 领取任务quest
 */
public class RQReceiveTask extends RQBase {
	/** 用户ID */
	public String userId;
	/** 任务ID */
	public String taskId;
	public String cityCode;//城市code

	public RQReceiveTask(String userId, String taskId,String cityCode) {
		super();
		this.userId = userId;
		this.taskId = taskId;
		this.cityCode = cityCode;
	}

	public RQReceiveTask() {
		super();
	}

	@Override
	public String toString() {
		return "RQReceiveTask[userId=" + userId + ",taskId=" + taskId + "]";
	}
}
