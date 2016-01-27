package com.user.model.download;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.renrentui.net.HttpRequestDigestImpl;
import com.renrentui.net.IHttpRequest;
import com.renrentui.requestmodel.RQCheckVersion;
import com.renrentui.resultmodel.RSCheckVersion;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiConstants;
import com.renrentui.util.ToastUtil;
import com.user.model.HttpRequest;

/**
 * 下载工具类
 */
public class DownLoadUtils {

	public static void checkoutAppVersion(Context context, boolean auto,
			String action) {
		DownLoadAsyncTask mUpdataTask = new DownLoadAsyncTask(context, auto,
				action,false);
		mUpdataTask.execute("");
	}
	public static void checkoutAppVersion(Context context, boolean auto,
										  String action,boolean isShowToast) {
		DownLoadAsyncTask mUpdataTask = new DownLoadAsyncTask(context, auto,
				action,isShowToast);
		mUpdataTask.execute("");
	}
	public static void checkoutAppVersion(Context context, boolean auto) {
		DownLoadAsyncTask mUpdataTask = new DownLoadAsyncTask(context, auto,false);
		mUpdataTask.execute("");
	}

	private static class DownLoadAsyncTask extends
			AsyncTask<String, Integer, String> {

		private Context mContext;
		private boolean isAuto;
		private String strAction;
		private boolean isShowToast;

		public DownLoadAsyncTask(Context mContext, boolean isAuto,boolean isShowToast) {
			super();
			this.mContext = mContext;
			this.isAuto = isAuto;
			this.isShowToast = isShowToast;
		}

		public DownLoadAsyncTask(Context con, boolean auto, String action,boolean isShowToast) {
			mContext = con;
			isAuto = auto;
			strAction = action;
			this.isShowToast = isShowToast;
		}

		@Override
		protected String doInBackground(String... arg0) {
			RQCheckVersion checkVersion = new RQCheckVersion("1", "1");
			String strResult = "";
			IHttpRequest httpRequest = new HttpRequestDigestImpl();
			try {
				strResult = HttpRequest.sendPostJSON(mContext,
						ApiConstants.checkVersionUrl + "common/services/common/versioncheck",
						checkVersion);
				if(strResult==null){
					strResult = "";
				}
			} catch (Exception e) {
				strResult = "";
			}
			return strResult.trim();
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				if (Util.IsNotNUll(result)) {
					Gson gson = new Gson();
					RSCheckVersion checkVersion = gson.fromJson(result,
							RSCheckVersion.class);
					if (checkVersion == null) {
						ToastUtil.show(mContext, "网络异常");
					} else if (checkVersion.data == null) {
						ToastUtil.show(mContext, "网络异常");
					} else {
						String t = checkVersion.data.version;
						String[] v = t.split("\\.");
						String str = "";
						for (int i = 0; i < v.length; i++) {
							str += v[i];
						}
						int version = Integer.parseInt(str);
						int mCurrentVersion = com.renrentui.util.Utils
								.getVersionId(mContext);
						if (version > mCurrentVersion) {
							DownLoadDialog mUpdateDialog = new DownLoadDialog(
									mContext, String.valueOf(version),
									checkVersion.data.updateUrl,
									checkVersion.data.isMust,
									checkVersion.data.message);
							mUpdateDialog.show();
						} else {
							if (isShowToast) {
								ToastUtil.show(mContext, "已为最新版本");
							}

						}
					}
				} else {
					//ToastUtil.show(mContext, "升级错误");
				}
			}catch (Exception e){

			}
		}
	}

}
