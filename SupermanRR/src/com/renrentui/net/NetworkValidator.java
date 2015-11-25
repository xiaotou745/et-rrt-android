package com.renrentui.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.renrentui.tools.Constants;

/**
 * 网络连接认证
 * @author back
 * @version landingtech_v1
 */
public class NetworkValidator {
	/**
	 * 判断是否有网络
	 */
	public static boolean isNetworkConnected(Context context){
		if(context !=null){
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getActiveNetworkInfo();
			if(info !=null){
				return info.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 获取当前连接网络的类型
	 * WIFI = WIFI, MOBILE = MOBILE, 没有网络 = NOTCONNECTED
	 */
	public static int getConnectedType(Context context){
		if(context != null){
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getActiveNetworkInfo();
			if(info != null && info.isAvailable()){
				return info.getType();
			}
		}
		return Constants.NOTCONNECTED;
	}
	
}
