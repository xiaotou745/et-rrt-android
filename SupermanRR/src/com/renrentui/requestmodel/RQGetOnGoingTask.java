package com.renrentui.requestmodel;

public class RQGetOnGoingTask extends RQBase {
	/**
	 * 用户id
	 */
	public String userId;
	/**
	 * 为分页处理服务，获取任务的个数。当为0或者不填的时候，默认全部获取
	 */
	public int ItemsCount = 10;
	/**
	 * 为分页处理服务，上页获取到的任务列表中的NextID，不填表示从头开始获取
	 */
	public String NextID;
	
	public RQGetOnGoingTask() {
		super();
	}

	public RQGetOnGoingTask(String userId, String nextID) {
		this.userId = userId;
		NextID = nextID;
	}
	
	public RQGetOnGoingTask(String userId, int itemsCount, String nextID) {
		super();
		this.userId = userId;
		ItemsCount = itemsCount;
		NextID = nextID;
	}

	@Override
	public String toString() {
		return "RQGetOnGoingTask[userId=" + userId + ",ItemsCount="
				+ ItemsCount + ",NextID=" + NextID + "]";
	}

}
