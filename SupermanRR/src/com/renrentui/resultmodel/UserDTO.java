package com.renrentui.resultmodel;

import java.io.Serializable;

public class UserDTO implements Serializable {
	/**
	 * 用户手机号码
	 */
	public String userId;

	/**
	 * 用户名称
	 */
	public String userName;

	public UserDTO(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserDTO[userId=" + userId + ",userName=" + userName + "]";
	}
}
