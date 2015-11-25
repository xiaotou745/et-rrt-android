package com.renrentui.tools;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * @version landingtech_v1
 * @author Eric
 *
 */
public class SharedPreferencesTools {
	private Context context;
	private String spName="landingtech";
	private static SharedPreferencesTools spTools;
	private SharedPreferencesTools(Context context) {
		this.context = context;
	}

	public static SharedPreferencesTools getSPInstance(Context context){
		if(spTools==null){
			spTools= new SharedPreferencesTools(context);
		}
		return spTools;
	}
	
	/**
	 * @param mode  Context.MODE_PRIVATE 模式
	 * @param param 参数
	 */
	public void setSharedPreferences( Map<String, String> param) {
		SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			editor.putString(entry.getKey(), entry.getValue());
		}
		editor.commit();
	}

	/**
	 * @param mode 模式
	 */
	public Map<String, String> getSharedPreferences() {
		SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		@SuppressWarnings("unchecked")
		Map<String,String>map= (Map<String, String>) sp.getAll();
		return map;
	}
}
