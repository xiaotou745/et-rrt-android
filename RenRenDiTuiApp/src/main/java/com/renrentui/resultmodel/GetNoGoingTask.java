package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.List;

/**
 * 未领取任务信息列表
 * 
 * @author llp
 * 
 */
public class GetNoGoingTask implements Serializable {
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
	public List<NoGoingTaskInfo> content;

	public GetNoGoingTask() {
		super();
	}

	public GetNoGoingTask(int total, int count, String nextId,
			List<NoGoingTaskInfo> content) {
		super();
		this.total = total;
		this.count = count;
		this.nextId = nextId;
		this.content = content;
	}

	@Override
	public String toString() {
		return "RSGetNoGoingTask[total=" + total + ",count=" + count
				+ ",nextId=" + nextId + ",content=" + content + "]";
	}
}
