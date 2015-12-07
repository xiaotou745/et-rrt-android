package com.renrentui.util;

/**
 * 任务资料的状态
 * 
 * @author llp
 * 
 */
public enum ToMainPage {
	审核中(0), 未通过(2), 已通过(1);
	private final int value;

	ToMainPage(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String Tag = "跳转main标志";
}
