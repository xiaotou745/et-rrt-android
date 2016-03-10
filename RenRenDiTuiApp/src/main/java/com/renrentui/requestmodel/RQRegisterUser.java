package com.renrentui.requestmodel;

import android.content.Context;

/**
 * 注册用户请求类
 * 
 * @author llp
 * 
 */
public class RQRegisterUser extends RQBase {

	/**
	 * 用户的手机号码
	 */
	private String phoneNo;
	/**
	 * 用户密码
	 */
	private String passWord;
	/**
	 * 手机获取验证码
	 */
	private String verifyCode;
	
	private String name;

	/**
	 * 推荐人
	 */
	private String recommendPhone;
	private String  operSystem="android";//

	public RQRegisterUser() {
		super();
	}

	public RQRegisterUser(String phoneNo, String passWord,
			String verifyCode,String name,String recommendPhone) {
		super();
		this.phoneNo = phoneNo;
		this.passWord = passWord;
		this.verifyCode = verifyCode;
		this.name = name;
		this.recommendPhone = recommendPhone;
	}
	public RQRegisterUser(String phoneNo, String passWord,
						  String verifyCode,String name) {
		super();
		this.phoneNo = phoneNo;
		this.passWord = passWord;
		this.verifyCode = verifyCode;
		this.name = name;
	}

	@Override
	public String toString() {
		return "RQRegisterUser[PhoneNo=" + phoneNo + ",Password=" + passWord
				+ ",VerifyCode=" + verifyCode + ",recommendPhone=" + recommendPhone +"]";
	}
}
