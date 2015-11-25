package com.renrentui.util;

/**
 * 我的任务
 * 
 * @author llp
 * 
 */
public enum ToMyTaskPage {
	审核通过(0), 过期失效(1), 已取消(2);
	private final int value;

	ToMyTaskPage(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String Tag = "跳转main标志";
}
