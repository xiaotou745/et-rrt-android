package com.renrentui.util;

import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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

	/**
	 * 数据加密
	 * @param data
	 * @return
	 */
	public static JSONObject securityMapToJSON(Map<String, String> data) {
		JSONObject resultObj = new JSONObject();
		try {
			JSONObject object = new JSONObject();
			for (Map.Entry<String, String> entry : data.entrySet()) {
				object.put(entry.getKey(), entry.getValue());
			}
			resultObj.put("data", Security.aesEncrypt(object.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return resultObj;
	}

	/**
	 * 结果数据解密
	 * @param key
	 * @param sercurityResult
	 * @return
	 */
	public static String securityResultToResult (String key,String sercurityResult){
			String strResult = "";
		try{
			if(TextUtils.isEmpty(key)){
				key = "data";
			}
			JSONObject jsonObject = new JSONObject(sercurityResult);
			strResult = aesDecrypt(jsonObject.getString(key));
		}catch (Exception e){

		}
return strResult;
	}

}
