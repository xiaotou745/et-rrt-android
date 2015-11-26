package com.renrentui.resultmodel;

import java.io.Serializable;

/**
 * 返回结果基类
 * 
 * @author EricHu
 * 
 */
public class RSBase implements Serializable {
	public String code;// 返回code
	public String msg;// 返回消息

	public RSBase(String Code, String Msg) {
		super();
		this.code = Code;
		this.msg = Msg;
	}

	public RSBase() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "RSBase [Code = " + code + ",Msg=" + msg + "]";
	}

}
