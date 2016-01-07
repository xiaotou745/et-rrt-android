package com.renrentui.tools;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 常用工具类
 * 
 * @author Eric
 * 
 */
public class Util {
	/**
	 * listview 高度重新定义
	 * 
	 * @param listView
	 */
	public static ViewGroup.LayoutParams setListViewHeightBasedOnChildren(
	        ListView listView) {
		
		ListAdapter listAdapter = listView.getAdapter();
		
		if (listAdapter == null) {
			
			// pre-condition
			
			return null;
			
		}
		
		int totalHeight = 0;
		
		for (int i = 0; i < listAdapter.getCount(); i++) {
			
			View listItem = listAdapter.getView(i, null, listView);
			
			listItem.measure(0, 0);
			
			totalHeight += listItem.getMeasuredHeight();
			
		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		
		params.height = totalHeight
		        + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		
		listView.setLayoutParams(params);
		return params;
	}
	
	/**
	 * GridView 高度重新定义
	 * 
	 */
	public static ViewGroup.LayoutParams setGridViewHeightBasedOnChildren(
	        GridView gridView) {
		
		ListAdapter listAdapter = gridView.getAdapter();
		
		if (listAdapter == null) {
			
			// pre-condition
			
			return null;
			
		}
		
		int totalHeight = 0;
		
		for (int i = 0; i < listAdapter.getCount(); i++) {
			
			View listItem = listAdapter.getView(i, null, gridView);
			
			listItem.measure(0, 0);
			
			totalHeight += listItem.getMeasuredHeight();
			
		}
		
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		
		params.height = totalHeight + ((listAdapter.getCount() - 1));
		
		gridView.setLayoutParams(params);
		return params;
	}
	
	/**
	 * @author rec
	 * @param number
	 * @return
	 */
	public static String getDoubleNumber(double number) {
		DecimalFormat df = new DecimalFormat("#.00");
		if (number == 0) {
			return "0";
		}
		else {
			return df.format(number);
		}
	}
	
	/**
	 * 得到两位数字
	 * 
	 * @author rec
	 * @param i
	 * @return
	 */
	public static String getTwoInt(int i) {
		DecimalFormat df = new DecimalFormat("00");
		return df.format(i);
	}
	
	/**
	 * 获取大图片
	 * 
	 * @param imgUrl
	 * @return
	 */
	public static String getLargePicture(String imgUrl) {
		try {
			StringBuffer stringBuffer = new StringBuffer();
			String string = imgUrl.trim();
			return stringBuffer
			        .append(string.substring(0, string.length() - 4))
			        .append("_ol.")
			        .append(string.substring(string.length() - 3,
			                string.length())).toString();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取小图片
	 * 
	 * @param imgUrl
	 * @return
	 */
	public static String getSmallPicture(String imgUrl) {
		try {
	        StringBuffer stringBuffer = new StringBuffer();
	        String string = imgUrl.trim();
	        return stringBuffer
	                .append(string.substring(0, string.length() - 4))
	                .append("_os.")
	                .append(string.substring(string.length() - 3,
	                        string.length())).toString();
        }
        catch (Exception e) {
	        return null;
        }
	}
	
	/**
	 * 获取日期string 2014-07-25
	 * 
	 * @param cutdays
	 *            往后 cutdays<0 往前 cutdays>0
	 * @return
	 */
	public static String getDayTimeString(int cutdays) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		calendar.add(Calendar.DAY_OF_MONTH, cutdays);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String monthString = Integer.toString(month);
		String dayString = Integer.toString(day);
		if (month > 12) {
			month = 1;
		}
		if (month < 10) {
			monthString = "0" + month;
		}
		if (day < 10) {
			dayString = "0" + day;
		}
		StringBuffer time = new StringBuffer();
		
		return time.append(year).append("-").append(monthString).append("-")
		        .append(dayString).toString();
		
	}
	
	/**
	 * 微信分享设置缩略图
	 * 
	 * @param bmp
	 * @param needRecycle
	 * @return
	 */
	public static byte[] bmpToByteArray(final Bitmap bmp,
	        final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	
	
	
	
	/**
	 * 得到资源文件中的文字
	 * 
	 * @param context
	 * @param resid
	 * @return
	 */
	public static String getString(Context context, int resid) {
		return context.getResources().getString(resid);
	}
	
	public static String getString(Context context, int resid1, int resid2) {
		return context.getResources().getString(resid1)
		        + context.getResources().getString(resid2);
	}
	
	public static String getString(Context context, int resid1, String str,
	        int resid2) {
		return context.getResources().getString(resid1) + str
		        + context.getResources().getString(resid2);
	}
	
	/**
	 * 冒号
	 * 
	 * @return
	 */
	public static String getColon() {
		return ": ";
	}
	
	/**
	 * 逗号
	 * 
	 * @return
	 */
	public static String getComma() {
		return ",";
	}
	

	
	/**
	 * 判断字符串是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean IsNotNUll(String string) {
		return string != null && !string.trim().equals("");
	}
	
	/**
	 * 判断字符串是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static String IsNotNull(String string) {
		if (string != null && !string.trim().equals("")) {
			return string;
		}
		return "";
	}
	/**
	 * 判断字符串是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean IsNull(String string) {
		return !Util.IsNotNUll(string);
	}
	
	/**
	 * 下载图片并保存到SD卡
	 * 
	 * @param path
	 *            图片网络路径
	 * @return
	 */
	public static Bitmap getBitmap(String path) {
		
		Bitmap bitmap = null;
		// 创建get对象
		HttpGet request = new HttpGet(path);
		// 创建HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		InputStream is = null;
		try {
			httpResponse = client.execute(request);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				is = httpResponse.getEntity().getContent();
				bitmap = BitmapFactory.decodeStream(is);
				String id_path = FileCache.getCacheDir() + "id.png";
				File file = new File(id_path);// 将要保存图片的路径
				BufferedOutputStream bos = new BufferedOutputStream(
				        new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
				is.close();
				bos.flush();
				bos.close();
			}
		}
		catch (Exception e) {
			return null;
		}
		return bitmap;
	}
	
	
	/**
	 * 获取小图
	 * 
	 * @param url
	 * @return
	 */
	public static String getImgSmall(String url) {
		if (url != null) {
			if (!url.equals("")) {
				url = url.substring(0, url.length() - 4) + "_os"
				        + url.substring(url.length() - 4);
			}
		}
		return url;
	}
	
	/**
	 * 获取大图
	 * 
	 * @param url
	 * @return
	 */
	public static String getImgBig(String url) {
		if (url != null) {
			if (!url.equals("")) {
				url = url.substring(0, url.length() - 4) + "_ol"
				        + url.substring(url.length() - 4);
			}
		}
		return url;
	}
	
	/**
	 * 判断是否首次进入软件
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isFirstEntry(Context context) {
		String isFirstEntry = SharedPreferencesTools.getSPInstance(context)
		        .getSharedPreferences().get("isFirstEntry");
		if (isFirstEntry == null) {
			Map<String, String> param = new HashMap<>();
			param.put("isFirstEntry", "entried");
			SharedPreferencesTools.getSPInstance(context).setSharedPreferences(
			        param);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 设置首次进入软件
	 * 
	 * @param context
	 */
	public static void setFirstEntry(Context context) {
		Map<String, String> param = new HashMap<>();
		param.put("isFirstEntry", null);
		SharedPreferencesTools.getSPInstance(context).setSharedPreferences(
		        param);
	}
	
	/**
	 * 卸载软件
	 */
	public static void setSpNull(Context context) {
		Map<String, String> param = new HashMap<>();
		param.put("user", null);
		param.put("isDynamic", null);
		param.put("Categorys", null);
		param.put("CategorysRecommend", null);
		SharedPreferencesTools.getSPInstance(context).setSharedPreferences(
		        param);
	}
	
	public static float getFloatSize(double i){
		return Float.parseFloat(Util.getDoubleNumber(i/1000000));		
	}
	
//	/*
//	 * 判断是否在android模拟器中
//	 */
//	public static boolean isEmulatorByBuildModel() 
//	{
//        Log.e("MODEL=",Build.MODEL);
//        Log.e("MANUFACTURER=",Build.MANUFACTURER);
//        return ( Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk") );
//    }
	/**
	 * 左边去除某个字符
	 * @param str
	 * @param trim
	 * @return
	 */
	public static String TrimLeftStr(String str, String trim) {
		if (str != null && trim != null && str.length() > trim.length()
		        && str.substring(0, trim.length()).equals(trim)) {
			return str.substring(trim.length());
		}
		return str;
	}
	/**
	 * 右边去除某个字符
	 * @param str
	 * @param trim
	 * @return
	 */
	public static String TrimRightStr(String str, String trim) {
		if (str != null && trim != null && str.length() > trim.length()
				&& str.substring(str.length()-trim.length(), str.length()).equals(trim)) {
			return str.substring(0,str.length()-trim.length());
		}
		return str;
	}
	/**
	 * 左右边去除某个字符
	 * @param str
	 * @param trim
	 * @return
	 */
	public static String TrimLeftRightStr(String str, String trim) {
		return TrimRightStr(TrimLeftStr(str, trim), trim);
	}

	/**
	 * 手机号验证
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {

		String telRegex = "1[0-9]{10}";
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}
	/**
	 * 邮箱验证
	 *
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || TextUtils.isEmpty(email)) {
			return false;
		}
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
