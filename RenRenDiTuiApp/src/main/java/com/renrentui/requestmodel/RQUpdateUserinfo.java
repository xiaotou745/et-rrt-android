package com.renrentui.requestmodel;

public class RQUpdateUserinfo extends RQBase {
	public String userId;
	public String userName;
	public String sex;
	public String age;
	public String headImage;
	public String birthDay;

	public RQUpdateUserinfo() {
		super();
	}

//	public RQUpdateUserinfo(String userId, String userName, String sex,
//			String age, String headImage) {
//		super();
//		this.userId = userId;
//		this.userName = userName;
//		this.sex = sex;
//		this.age = age;
//		this.headImage = headImage;
//	}

	public RQUpdateUserinfo(String userId, String userName, String sex, String age, String headImage, String birthDay) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.sex = sex;
		this.age = age;
		this.headImage = headImage;
		this.birthDay = birthDay;
	}

	@Override
	public String toString() {
		return "RQUpdateUserinfo{" +
				"userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", sex='" + sex + '\'' +
				", age='" + age + '\'' +
				", headImage='" + headImage + '\'' +
				", birthDay='" + birthDay + '\'' +
				'}';
	}
}
