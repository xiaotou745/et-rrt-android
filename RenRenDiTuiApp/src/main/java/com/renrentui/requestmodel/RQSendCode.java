package com.renrentui.requestmodel;

public class RQSendCode extends RQBase {
	public String phoneNo;
	public int sType;

	public RQSendCode() {
		super();
	}

	public RQSendCode(String phoneNo, int sType) {
		super();
		this.phoneNo = phoneNo;
		this.sType = sType;
	}

	@Override
	public String toString() {
		return "RQSendCode[phoneNo=" + phoneNo + ",sType=" + sType + "]";
	}
}
