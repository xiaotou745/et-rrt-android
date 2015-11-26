package com.renrentui.requestmodel;

/**
 * @author llp
 * 
 */
public class RQCheckVersion extends RQBase {
	/** 客户端类型 1:Android 2 :IOS */
	public String platForm;
	/** 用户版本 1 地推员 2 商家 */
	public String userType;

	public RQCheckVersion(String platForm, String userType) {
		super();
		this.platForm = platForm;
		this.userType = userType;
	}

	public RQCheckVersion() {
		super();
	}

	@Override
	public String toString() {
		return "RQCheckVersion[platForm=" + platForm + ",userType=" + userType
				+ "]";
	}
}
