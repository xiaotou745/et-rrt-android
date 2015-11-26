package com.renrentui.requestmodel;

public class RQGetFinishedTask extends RQBase {
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
	 * 1审核通过2已取消3已失效 4 当前任务审核中列表 5 当前任务未通过列表
	 */
	public int orderType;

	public RQGetFinishedTask() {
		super();
	}

	public RQGetFinishedTask(String userId, String nextId, int orderType) {
		super();
		this.userId = userId;
		this.nextId = nextId;
		this.orderType = orderType;
	}

	public RQGetFinishedTask(String userId, int itemsCount, String nextId,
			int orderType) {
		super();
		this.userId = userId;
		this.itemsCount = itemsCount;
		this.nextId = nextId;
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return "RQGetOnGoingTask[userId=" + userId + ",itemsCount="
				+ itemsCount + ",nextId=" + nextId + ",orderType=" + orderType
				+ "]";
	}

}
