package com.renrentui.resultmodel;

/**
 * User 用户信息 UserDTO
 * 
 * @author EricHu
 * 
 */
public class RSUser extends RSBase {
	/**
	 * 用户信息
	 */
	public UserDTO data;

	public RSUser() {
		super();
	}

	public RSUser(String Code, String Message, UserDTO user) {
		super(Code, Message);
		this.data = user;
	}

	@Override
	public String toString() {
		return "RSUser [user=" + data + ",code=" + code + ",msg=" + msg
				+ "]";
	}

}
