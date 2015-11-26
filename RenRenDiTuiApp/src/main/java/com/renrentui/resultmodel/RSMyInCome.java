package com.renrentui.resultmodel;

public class RSMyInCome extends RSBase {
	public MyInCome data;

	public RSMyInCome(String Code, String Msg, MyInCome data) {
		super(Code, Msg);
		this.data = data;
	}

	public RSMyInCome(String Code, String Msg) {
		super(Code, Msg);
	}

	public RSMyInCome() {
		super();
	}

	@Override
	public String toString() {
		return "RSMyInCome[data=" + data + "]";
	}

}
