package com.renrentui.requestmodel;

import android.content.Context;

import com.renrentui.util.ApiConstants;

/**
 * 登录信息
 */
public class RQLogin extends RQBase {
	
	/**
	 * 用户手机号码
	 */
	public String phoneNo;
	
	/**
	 * 用户密码
	 */
	public String passWord;

	public String sSID ;//SSID标识 否
	public String operSystem ;//手机操作系统android,ios 否
	public String operSystemModel ;//手机具体型号5.0 否
	public String phoneType ;//手机类型,三星、苹果 否
	public String appVersion ;//版本号 否


	public RQLogin(Context context){
		super();
	}
	
	public RQLogin(Context context, String phoneNo,String passWord) {
		super();
		this.phoneNo = phoneNo;
		this.passWord = passWord;
	}

	public RQLogin(Context context,  String phoneNo, String passWord, String sSID, String operSystemModel, String phoneType) {
		this.phoneNo = phoneNo;
		this.passWord = passWord;
		this.sSID = sSID;
		this.operSystem = "android";
		this.operSystemModel = operSystemModel;
		this.phoneType = phoneType;
		this.appVersion = ApiConstants.APP_VERSION;
	}

	@Override
	public String toString() {
		return "RQLogin{" +
				"phoneNo='" + phoneNo + '\'' +
				", passWord='" + passWord + '\'' +
				", sSID='" + sSID + '\'' +
				", operSystem='" + operSystem + '\'' +
				", operSystemModel='" + operSystemModel + '\'' +
				", phoneType='" + phoneType + '\'' +
				", appVersion='" + appVersion + '\'' +
				'}';
	}
}
