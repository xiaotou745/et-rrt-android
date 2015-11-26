package com.renrentui.requestmodel;

import android.content.Context;

public class RQLogin extends RQBase {
	
	/**
	 * 用户手机号码
	 */
	public String phoneNo;
	
	/**
	 * 用户密码
	 */
	public String passWord;
	
	public RQLogin(Context context){
		super();
	}
	
	public RQLogin(Context context, String phoneNo,String passWord) {
		super();
		this.phoneNo = phoneNo;
		this.passWord = passWord;
	}
	
	@Override
	public String toString() {
		return "RQLogin[phoneNo="+phoneNo+",passWord="+passWord+"]";
	}
}
