package base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.renrentui.app.R;
import com.renrentui.tools.ExitApplication;
import com.renrentui.util.Utils;
import com.umeng.analytics.MobclickAgent;

public class BaseFragmentActivity extends FragmentActivity {

	//title
	public ImageView mIV_title_left;
	public TextView mTV_title_left;
	public TextView mTV_title_content;
	public TextView mTV_title_right;
	public ImageView mIV_title_right;
	public Context context;
	public String strUserId = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context =this;
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
		//onBack(null);
		mIV_title_left = (ImageView)findViewById(R.id.iv_title_left);
		mTV_title_left = (TextView)findViewById(R.id.tv_title_left);
		mTV_title_content = (TextView)findViewById(R.id.tv_title_content);
		mTV_title_right = (TextView)findViewById(R.id.tv_title_right);
		mIV_title_right = (ImageView)findViewById(R.id.iv_title_right);
		//onBack(null);
	}

//	/**
//	 * 返回操作统一处理或者定制处理
//	 *
//	 * @param iBack
//	 */
//	public void onBack(IBack iBack) {
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
//					((FragmentActivity) v.getContext()).finish();
//				}
//			});
//		}
//	}
	protected void onResume() {
		super.onResume();
		if (Utils.getUserDTO(context) != null){
			strUserId = Utils.getUserDTO(context).data.userId;
		}else {
			strUserId = "0";
		}
		MobclickAgent.onResume(this);       //统计时长
	}
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
//	接口
	public interface MyTaskInterface {
	/**
	 *
	 * @param num1  进行中
	 * @param num2  已过期
	 */
	void showMyTaskCount(String num1, String num2);
	}
	public interface  MyTaskMateriaInterface{
		/**
		 *
		 * @param num1  审核中
		 * @param num2   通过
		 * @param num3    拒绝
		 */
		void showMyTaskMateriaCount(int num1, int num2, int num3);
	}
}
