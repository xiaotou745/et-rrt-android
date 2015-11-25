package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.List;

public class GetOnGoingTask implements Serializable {
	/**
	 * 总任务个数
	 */
	public int total;
	/**
	 * 当前获取到的任务个数
	 */
	public int count;
	/**
	 * 为分页处理服务，下次如果继续获取，需要填写该ID
	 */
	public String nextID;
	/**
	 * 已领取任务详情集合
	 */
	public List<OnGoingTaskInfo> content;
	/** 已领取数量 */
	public int receivedCount;
	/** 已通过数量 */
	public int passCount;
	/** 审核中数量 */
	public int noPassCount;

	public GetOnGoingTask() {
		super();
	}

	public GetOnGoingTask(int total, int count, String nextID,
			List<OnGoingTaskInfo> content, int receivedCount, int passCount,
			int noPassCount) {
		super();
		this.total = total;
		this.count = count;
		this.nextID = nextID;
		this.content = content;
		this.receivedCount = receivedCount;
		this.passCount = passCount;
		this.noPassCount = noPassCount;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "GetOnGoingTask[total=" + total + ",count=" + count + ",nextID="
				+ nextID + ",content=" + content + "]";
	}

}
