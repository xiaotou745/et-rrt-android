package base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.renrentui.app.MyApplication;
import com.renrentui.app.R;
import com.renrentui.controls.MyProgersssDialog;
import com.renrentui.interfaces.IBack;
import com.renrentui.interfaces.INodata;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.tools.ExitApplication;
import com.renrentui.tools.Util;
import com.renrentui.util.Utils;
import com.umeng.analytics.MobclickAgent;

/**
 * 基础页面
 */
public class BaseActivity extends Activity {
	public MyApplication mMyApplication =null;
	//无数据
	public View layout_nodata;
	public Button btn_nodata;
	public TextView tv_nodata;

	public MyProgersssDialog progersssDialog;
	public Context context;
	//title
	public ImageView mIV_title_left;
	public TextView mTV_title_left;
	public TextView mTV_title_content;
	public TextView mTV_title_right;
	public ImageView mIV_title_right;

	public String strUserId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context =this;
		if(mMyApplication==null){
			mMyApplication = (MyApplication)getApplication();
		}
		initImageLoader(getApplicationContext());
	}

	private void initImageLoader(Context context){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(5 * 1024 * 1024))  
                .memoryCacheSize(5 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}

	
	/**
	 * 初始化基类设置 在子类setContentView之后调用
	 */
	protected void init() {
		ExitApplication.getInstance().addActivity(this);
		layout_nodata = findViewById(R.id.layout_nodata);
		btn_nodata = (Button) findViewById(R.id.btn_nodata);
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		progersssDialog = new MyProgersssDialog(this);
		progersssDialog.setCancelable(false);
		mIV_title_left = (ImageView)findViewById(R.id.iv_title_left);
		mTV_title_left = (TextView)findViewById(R.id.tv_title_left);
		mTV_title_content = (TextView)findViewById(R.id.tv_title_content);
		mTV_title_right = (TextView)findViewById(R.id.tv_title_right);
		mIV_title_right = (ImageView)findViewById(R.id.iv_title_right);
		//onBack(null);
	}



	/**
	 * 页面没有数据时的统一处理
	 * @param resultMsgType
	 * @param btnText 按钮上面显示的文字
	 * @param tvText	没有数据对应的后续操作
	 * @param iNodata
	 */
	public void onNodata(int resultMsgType, String btnText, String tvText,
			final INodata iNodata) {
		layout_nodata.setVisibility(View.VISIBLE);
		if (Util.IsNotNUll(tvText)) {
			tv_nodata.setText(tvText);
		} else {
			tv_nodata.setText("点击刷新页面！");
		}
		if (Util.IsNotNUll(btnText)) {
			btn_nodata.setText(btnText);
		} else {
			btn_nodata.setText("刷新");
		}
		if (iNodata != null) {
			btn_nodata.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					iNodata.onNoData();
				}
			});
		}
		switch (resultMsgType) {
		case ResultMsgType.NetworkNotValide:
			tv_nodata.setText("网络无连接！");
			btn_nodata.setText("设置");
			btn_nodata.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Settings.ACTION_SETTINGS);
					startActivity(intent);
				}
			});
			break;
		case ResultMsgType.ServiceErr:
			break;
		case ResultMsgType.ServiceExp:
			break;
		case ResultMsgType.Success:
			break;
		default:
			break;
		}
	}

	/**
	 * 页面有数据时隐藏没有数据的界面
	 */
	public void hideLayoutNoda() {
		layout_nodata.setVisibility(View.GONE);
	}

	/**
	 * 显示加载动画
	 */
	public void showProgressDialog() {
		if (this.isFinishing()) {
			return;
		}
		if (progersssDialog == null) {
			progersssDialog = new MyProgersssDialog(this);
		}
		progersssDialog.show();
	}

	/**
	 * 隐藏加载动画
	 */
	public void hideProgressDialog() {
		if (this.isFinishing()) {
			return;
		}
		if (progersssDialog != null) {
			progersssDialog.hide();
		}
	}
	/**
	 * 销毁加载动画
	 */
	public void dismissProgressDialog() {
		if (this.isFinishing()) {
			return;
		}
		if (progersssDialog != null) {
			progersssDialog.cancel();
		}
	}
//	/**
//	 * 返回操作统一处理或者定制处理
//	 *
//	 * @param iBack
//	 */
////	public void onBack(IBack iBack) {
//		if (layout_back == null) {
//			return;
//		}
//		if (iBack != null) {
//			layout_back.setOnClickListener(iBack);
//		} else {
//			layout_back.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					((Activity) v.getContext()).finish();
//				}
//			});
//		}
//	}
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Utils.getUserDTO(context) != null){
			strUserId = Utils.getUserDTO(context).data.userId;
		}else {
			strUserId = "0";
		}
		MobclickAgent.onResume(this);

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dismissProgressDialog();
	}

	/**
	 * 判断用户是否登录
	 * @return
	 */
	protected boolean isLogin(){
		return !(TextUtils.isEmpty(strUserId) || "0".equals(strUserId));
	}


}
