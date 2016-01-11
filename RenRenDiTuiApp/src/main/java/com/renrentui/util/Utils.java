package com.renrentui.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

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
		String regex = "^(https?|ftp|file|www)(://)?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
		Pattern patt = Pattern. compile(regex);
		Matcher matcher = patt.matcher(strUrl);
		return   matcher.matches();
	}
	/**
	 * 判断gps是否开启
	 *
	 * @param context
	 * @return
	 */
	public static final boolean isOpenGPS(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		// if (gps || network) {
		// return true;
		// }
		return gps;

	}
	/**
	 * 强制开启gps
	 *
	 * @param context
	 * @throws CanceledException
	 */
	public static final void openGPS(Context context) throws PendingIntent.CanceledException {
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
	}
	/**
	 * 获取当前程序的版本号
	 *
	 * @param cx
	 * @return
	 */
	public static String getVersionName(Context cx) {
		return getPackageInfo(cx).versionName;
	}

	/**
	 * 获取当前程序的内部版本号
	 *
	 * @param cx
	 * @return
	 */
	public static int getVersionCode(Context cx) {
		return getPackageInfo(cx).versionCode;
	}

	/**
	 * 或得程序名称
	 *
	 * @param cx
	 * @return
	 */
	public static String getAppName(Context cx) {
		PackageInfo pi = getPackageInfo(cx);
		PackageManager packageManager = cx.getPackageManager();
		return packageManager.getApplicationLabel(pi.applicationInfo).toString();
	}
	/**
	 * 获得包信息
	 *
	 * @param c
	 * @return
	 */
	private static PackageInfo getPackageInfo(Context c) {
		try {
			return c.getPackageManager().getPackageInfo(c.getPackageName(), PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
		}
		return null;
	}
	// 获取手机类型
	public static String getModel(Context cx) {
		return Build.MODEL;
	}
	//获取手机系统版本
	public static String getModelSysVersion(Context cx){
		return Build.VERSION.RELEASE;
	}
	/**
	 * 获得国际移动设备身份码
	 *
	 * @param context
	 * @return
	*/
	public static String getMobileDevieceId(Context context) {
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
	/**
	 * 正整数
	 *
	 * @param orginal
	 * @return
	 */
	public static boolean isPositiveInteger(String orginal) {
		return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
	}
	private static boolean isMatch(String regex, String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(orginal);
		return isNum.matches();
	}


}

