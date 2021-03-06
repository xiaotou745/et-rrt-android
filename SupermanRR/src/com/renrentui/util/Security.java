package com.renrentui.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * 数据加密类
 * @author 
 */
public class Security {
	final static String AES_KEY = "Q*2_4@c!4kd^j&g%";

	/**
	 * 字符串对称加密
	 * 
	 * @param str
	 *            待加密字符串
	 * @return 加密后字符串
	 */
	public static String aesEncrypt(String str) {
		try {
			String password = AES_KEY;
			SecretKeySpec skeySpec = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			String strTmp = Base64.encodeToString(cipher.doFinal(str.getBytes()), Base64.NO_WRAP);
			return strTmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 字符串对称解密
	 * 
	 * @param str
	 *            待解密字符串
	 * @return 解密后字符串
	 */
	public static String aesDecrypt(String str) {
		try {
			String password = AES_KEY;
			SecretKeySpec skeySpec = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			String strTmp = new String(cipher.doFinal(Base64.decode(str, Base64.NO_WRAP)));
			return strTmp;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}
}
