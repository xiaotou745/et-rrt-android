package com.renrentui.resultmodel;

import java.io.Serializable;

public class UserInfo implements Serializable {
	/** 用户ID */
	public String userId;
	/** 用户姓名 */
	public String userName;
	/** 手机号 */
	public String phoneNo;
	/** 用户头像地址 */
	public String headImage;
	/** 城市编码 */
	public String cityCode;
	/** 用户头像全地址 */
	public String fullHeadImage;
	/** 城市名称 */
	public String cityName;
	/** 用户性别 */
	public String sex;
	/** 用户年龄 */
	public String age;
	/** 用户学历 */
	public String education;

	public UserInfo() {
		super();
	}

	public UserInfo(String userId, String userName, String phoneNo,
			String headImage, String cityCode, String cityName, String sex,
			String age, String education) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phoneNo = phoneNo;
		this.headImage = headImage;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.sex = sex;
		this.age = age;
		this.education = education;
	}

	@Override
	public String toString() {
		return "UserInfo[userId=" + userId + ",userName=" + userName
				+ ",phoneNo=" + phoneNo + ",headImage=" + headImage
				+ ",cityCode=" + cityCode + ",cityName=" + cityName + ",sex="
				+ sex + ",age=" + age + ",education=" + education + "]";
	}

}
