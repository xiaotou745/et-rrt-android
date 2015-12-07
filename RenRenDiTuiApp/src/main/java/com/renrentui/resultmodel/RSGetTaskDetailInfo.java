package com.renrentui.resultmodel;

/**
 * 任务详情的返回结果
 */
public class RSGetTaskDetailInfo extends RSBase {
	public TaskDetailInfo data;

	public RSGetTaskDetailInfo() {
		super();
	}

	public RSGetTaskDetailInfo(String Code, String Msg, TaskDetailInfo data) {
		super(Code, Msg);
		this.data = data;
	}

	@Override
	public String toString() {
		return "RSGetOnGoingTask[data=" + data + "]";
	}
}
