package com.renrentui.util;

/**
 * 我的任务
 * 
 * @author llp
 * 
 */
public enum ToMyTaskPage {
	进行中(0), 已过期(1);
	private final int value;

	ToMyTaskPage(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String Tag = "跳转main标志";
}
