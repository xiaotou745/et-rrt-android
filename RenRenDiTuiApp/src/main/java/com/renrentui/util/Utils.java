package com.renrentui.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import com.renrentui.resultmodel.RSUser;
import com.renrentui.tools.GsonTools;
import com.renrentui.tools.SharedPreferencesTools;
import com.user.activity.LoginActivity;

/**
 * 本项目常用的工具
 * 
 * @author llp
 * 
 */
public final class Utils {
	/**
	 * 获取会员用户
	 *
	 * @param context
	 * @return
	 */
	public static RSUser getUserDTO(Context context) {
		try {
			String consultantVO = SharedPreferencesTools.getSPInstance(context)
					.getSharedPreferences().get("UserDTO");
			if (IsNotNUll(consultantVO)) {
				return GsonTools.jsonToBean(consultantVO, RSUser.class);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 设置会员用户
	 *
	 * @param context
	 * @param consultantVO
	 */
	public static void setUserDTO(Context context, RSUser consultantVO) {
		try {
			if (consultantVO != null) {
				String consultantVO1 = GsonTools.objectToJson(consultantVO);
				Map<String, String> map = new HashMap<>();
				if (IsNotNUll(consultantVO1)) {
					map.put("UserDTO", consultantVO1);
					SharedPreferencesTools.getSPInstance(context)
							.setSharedPreferences(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 退出登录
	 *
	 * @param context
	 */
	public static void quitUser(Context context) {
		Map<String, String> map = new HashMap<>();
		map.put("UserDTO", "");
		SharedPreferencesTools.getSPInstance(context).setSharedPreferences(map);
	}

	/**
	 * 跳转登陆页面
	 *
	 * @param context
	 */
	public static void toLogin(Context context) {
		Intent intent = new Intent();
		intent.setClass(context, LoginActivity.class);
		context.startActivity(intent);
	}

	/**
	 * 判断字符串是否为空
	 *
	 * @param string
	 * @return true 不为空;false 为空
	 */
	public static boolean IsNotNUll(String string) {
		return string != null && !string.trim().equals("");
	}

	/**
	 * 获取当前版本名
	 *
	 * @return 当前app的版本号
	 */
	public static String getVersion(Context context) {
		PackageManager manager;
		PackageInfo info = null;
		manager = context.getPackageManager();
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info.versionName;
	}

	/**
	 * 获取当前版本号
	 *
	 * @return 当前app的版本号
	 */
	public static int getVersionId(Context context) {
		PackageManager manager;
		PackageInfo info = null;
		manager = context.getPackageManager();
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return info.versionCode;
	}

	/**
	 * 拨打电话（页面）
	 *
	 * @param strPhone
	 */
	public static void callPhone(Context con, String strPhone) {
		Uri telUri = Uri.parse("tel:" + strPhone);
		Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
		con.startActivity(intent);
	}

	/**
	 * 判断url是否合法
	 * @param strUrl
	 * @return
	 */
	public static   boolean checkUrl(String strUrl){
		if(!Utils.IsNotNUll(strUrl)){
			return false;
		}
		String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
		Pattern patt = Pattern. compile(regex);
		Matcher matcher = patt.matcher(strUrl);
		return   matcher.matches();
	}

}

