package com.renrentui.resultmodel;

public class RSCheckVersion extends RSBase {
	public NowVersion data;

	public RSCheckVersion(String Code, String Msg, NowVersion data) {
		super(Code, Msg);
		this.data = data;
	}

	public RSCheckVersion() {
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "RSCheckVersion[data=" + data + "]";
	}
}
