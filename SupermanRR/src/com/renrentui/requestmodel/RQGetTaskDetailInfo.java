package com.renrentui.requestmodel;

public class RQGetTaskDetailInfo extends RQBase {
	/**
	 * 用户id
	 */
	public String userId;
	/**
	 * 任务Id
	 */
	public String taskId;
	/**
	 * 已提交任务的orderId
	 */
	public String orderId;

	public RQGetTaskDetailInfo() {
		super();
	}

	public RQGetTaskDetailInfo(String userId, String taskId,
			String orderId) {
		this.userId = userId;
		this.taskId = taskId;
		this.orderId = orderId;
	}
	public RQGetTaskDetailInfo(String userId, String taskId) {
		this.userId = userId;
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return "RQGetTaskDetailInfo[userId=" + userId + ",taskId=" + taskId
				+ ",orderId=" + orderId + "]";
	}
}
