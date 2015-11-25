package com.renrentui.util;

import com.renrentui.resultmodel.RSBase;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司工具类
 * @author llp
 *
 */
public class ToastUtil {

	public static void show(Context context, RSBase rsBase) {
		String toastMessage = "";
		switch (rsBase.msg) {
		default:
			toastMessage = rsBase.msg;
			break;
		}
		if (Utils.IsNotNUll(toastMessage)) {
			Toast.makeText(context, toastMessage, 300).show();
		}
	};

	public static void show(Context context, String message) {
		Toast.makeText(context, message, 300).show();
	}

}
