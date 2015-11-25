package com.renrentui.resultmodel;

public class RSUserInfo extends RSBase {
	public UserInfo data;

	public RSUserInfo(String Code, String Msg, UserInfo data) {
		super(Code, Msg);
		this.data = data;
	}

	public RSUserInfo(String Code, String Msg) {
		super(Code, Msg);
	}

	public RSUserInfo() {
	}

	@Override
	public String toString() {
		return "RSUserInfo[data=" + data + "]";
	}

}
