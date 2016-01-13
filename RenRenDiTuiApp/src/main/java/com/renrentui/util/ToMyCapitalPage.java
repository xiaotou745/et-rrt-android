package com.renrentui.util;

/**
 * 资金明细
 * 
 * @author llp
 * 
 */
public enum ToMyCapitalPage {
	收入(0),支出(1);
	private final int value;

	ToMyCapitalPage(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
