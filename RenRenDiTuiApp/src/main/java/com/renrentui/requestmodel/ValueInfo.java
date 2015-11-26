package com.renrentui.requestmodel;

import java.io.Serializable;

public class ValueInfo implements Serializable {
	public String controlName;
	public String controlValue;

	public ValueInfo() {
		super();
	}

	public ValueInfo(String controlName, String controlValue) {
		super();
		this.controlName = controlName;
		this.controlValue = controlValue;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ValueInfo[controlName=" + controlName + ",controlValue="
				+ controlValue + "]";
	}
}
