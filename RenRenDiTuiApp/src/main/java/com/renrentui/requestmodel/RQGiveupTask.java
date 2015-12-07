package com.renrentui.requestmodel;

/**
 * 放弃任务
 */
public class RQGiveupTask extends RQBase {
	/** 用户ID */
	public String userId;
	/** 订单ID */
	public String orderId;
	/** 取消原因 */
	public String remark;

	public RQGiveupTask() {
		super();
	}

	public RQGiveupTask(String userId, String orderId, String remark) {
		super();
		this.userId = userId;
		this.orderId = orderId;
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "RQGiveupTask[userId=" + userId + ",orderId=" + orderId
				+ ",remark=" + remark + "]";
	}
}
