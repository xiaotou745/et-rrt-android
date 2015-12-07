package com.renrentui.resultmodel;

import java.util.List;

/**
 * 获取未领取任务信息类结果类
 * 
 * @author llp
 * 
 */
public class RSGetNoGoingTask extends RSBase {
	public GetNoGoingTask data;

	public RSGetNoGoingTask() {

		super();
	}

	public RSGetNoGoingTask(String Code, String Msg, GetNoGoingTask data) {
		super(Code, Msg);
		this.data = data;
	}

	@Override
	public String toString() {
		return "RSGetNoGoingTask[data=" + data + "]";
	}
}
