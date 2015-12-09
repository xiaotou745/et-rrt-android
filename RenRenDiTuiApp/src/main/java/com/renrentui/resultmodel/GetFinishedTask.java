package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.List;

public class GetFinishedTask implements Serializable {
	/**
	 * 总任务个数
	 */
	public int total;
	/**
	 * 当前获取到的任务个数
	 */
	public int count;
	/**
	 * 下次获取需要填写的ID 为分页处理服务
	 */
	public String nextId;
	/**
	 * 任务详情集合
	 */
	public List<FinishedTaskInfo> content;
	/** 已领取数量 */
	public int receivedCount;
	/** 审核中数量 */
	public int passCount;
	/** 未通过数量 */
	public int noPassCount;

	public GetFinishedTask() {
		super();
	}

	public GetFinishedTask(int total, int count, String nextId,
			List<FinishedTaskInfo> content, int receivedCount,
			int passCount, int noPassCount) {
		super();
		this.total = total;
		this.count = count;
		this.nextId = nextId;
		this.content = content;
		this.receivedCount = receivedCount;
		this.passCount = passCount;
		this.noPassCount = noPassCount;
	}

	@Override
	public String toString() {
		return "RSGetNoGoingTask[total=" + total + ",count=" + count
				+ ",nextId=" + nextId + ",content=" + content
				+ ",receivedCount=" + receivedCount + ",passCount=" + passCount
				+ ",noPassCount=" + noPassCount + "]";
	}
}
