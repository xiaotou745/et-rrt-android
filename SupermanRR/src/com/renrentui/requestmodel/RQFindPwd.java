package com.renrentui.requestmodel;

import android.content.Context;

public class RQFindPwd extends RQBase {

	/**
	 * 用户手机号码
	 */
	public String phoneNo;

	/**
	 * 用户新密码
	 */
	public String passWord;

	/**
	 * 手机获取验证码
	 */
	public String verifyCode;

	public RQFindPwd(Context context) {
		super();
	}

	public RQFindPwd(Context context, String phoneNo, String passWord,
			String verifyCode) {
		super();
		this.phoneNo = phoneNo;
		this.passWord = passWord;
		this.verifyCode = verifyCode;
	}

	@Override
	public String toString() {
		return "RQFindPwd[phoneNo=" + phoneNo + ",passWord=" + passWord
				+ ",verifyCode=" + verifyCode + "]";
	}
}
