package com.renrentui.util;

/**
 * 跳转到mainactivity某一页面
 * 
 * @author llp
 * 
 */
public enum ToMainPage {
	审核中(1), 未通过(2), 已领取(0);
	private final int value;

	ToMainPage(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String Tag = "跳转main标志";
}
