package com.renrentui.requestmodel;

public class RQUpdateUserinfo extends RQBase {
	public String userId;
	public String userName;
	public String sex;
	public String age;
	public String headImage;

	public RQUpdateUserinfo() {
		super();
	}

	public RQUpdateUserinfo(String userId, String userName, String sex,
			String age, String headImage) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.sex = sex;
		this.age = age;
		this.headImage = headImage;
	}

	@Override
	public String toString() {
		return "RQUpdateUserinfo[userId=" + userId + ",userName=" + userName
				+ ",sex=" + sex + ",age=" + age + ",headImage=" + headImage
				+ "]";
	}
}
