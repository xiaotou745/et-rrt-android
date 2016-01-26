package com.task.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import base.BaseFragment;
import base.BaseFragmentActivity;

import com.renrentui.app.R;
import com.renrentui.tools.ExitApplication;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.Utils;
import com.task.model.LayoutMainTopmenu;
import com.task.adapter.MyFragmentPagerAdapter;

/**
 * 任务的资料审核详情列表
 * @author llp
 * 
 */
public class MyTaskMaterialActivity extends BaseFragmentActivity implements
		OnClickListener , BaseFragmentActivity.MyTaskMateriaInterface {
	public ViewPager vp_task_main;
	private MyFragmentPagerAdapter viewPagerAdapter;
	private List<BaseFragment> fragmentList;
	private FragmentOnGoingTash fragment_onGoingTask;//审核中的资料
	private FragmentGoneTask fragment_GoneTask;//审核通过的资料
	private FragmentFinishedTask fragment_finishedTask;// 未通过的资料
	private LayoutMainTopmenu layoutTopMenu;// 顶部按钮
	private Button mBtn_submit_taskTemple;//提交资料

	private int topage = ToMainPage.审核中.getValue();// intent指向要显示的页面
	public String str_taskId = "";//任务id
	public String str_taskName = "";//任务名称
	public String str_taskType= "";//任务类型
	public String str_userId = "";//用户id
	public String str_ctId = "";//地推关系id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_main);
		super.init();
		ExitApplication.getInstance().addActivity(this);
		topage = getIntent().getIntExtra("topage", topage);
		str_taskId = getIntent().getStringExtra("TASK_ID");
		str_taskName = getIntent().getStringExtra("TASK_NAME");
		str_userId = Utils.getUserDTO(context).data.userId;
		str_ctId = getIntent().getStringExtra("ctId");
		str_taskType = getIntent().getStringExtra("TASK_TYPENAME");
		initView();
		initViewPager(topage);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}
//	@Override
//	protected void onStop(){
//		super.onStop();
//		//如果页面离开，就认为
//		isShowSubmitBtn = false;
//	}

//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		if (intent != null) {
//			topage = intent.getIntExtra("to", ToMainPage.审核中.getValue());
//		}
//		vp_task_main.setCurrentItem(topage);
//	}

	private void initView() {
		if(mIV_title_left!=null){
			mIV_title_left.setVisibility(View.VISIBLE);
			mIV_title_left.setOnClickListener(this);
		}
		if(mTV_title_content!=null){
			mTV_title_content.setText(str_taskName);
		}

		vp_task_main = (ViewPager) findViewById(R.id.vp_task_main);
		layoutTopMenu = new LayoutMainTopmenu(context);
		layoutTopMenu.setOnClickListener(this);
		mBtn_submit_taskTemple = (Button)findViewById(R.id.btn_submit_taskTemple);
		mBtn_submit_taskTemple.setOnClickListener(this);
	}

	public void initViewPager(int index) {
		fragment_onGoingTask = new FragmentOnGoingTash(str_taskId);
		fragment_GoneTask = new FragmentGoneTask(str_taskId);
		fragment_finishedTask = new FragmentFinishedTask(str_taskId);
		fragmentList = new ArrayList<BaseFragment>();
		fragmentList.add(0,fragment_onGoingTask);
		fragmentList.add(1,fragment_GoneTask);
		fragmentList.add(2,fragment_finishedTask);
		viewPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), context, fragmentList);
		vp_task_main.setAdapter(viewPagerAdapter);
		vp_task_main.setOffscreenPageLimit(0);
		vp_task_main.setOnPageChangeListener(new MyOnPageChangeListener());
		vp_task_main.setCurrentItem(index);
		layoutTopMenu.selected(index);
	}

	/**
	 * 提交资料
	 */
	private void showSubmitTaskTemple(){
		Intent mIntent= new Intent();
		mIntent.setClass(context, TaskDatumSubmitActiviyt.class);
		mIntent.putExtra("taskId", str_taskId);
		mIntent.putExtra("taskName", str_taskName);
		mIntent.putExtra("ctId",str_ctId);
		startActivity(mIntent);

	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那。

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// 表示在前一个页面滑动到后一个页面的时候，在前一个页面滑动前调用的方法。
		}

		@Override
		public void onPageSelected(int position) {
			// arg0是表示你当前选中的页面，这事件是在你页面跳转完毕的时候调用的。
			topage = position;
			layoutTopMenu.selected(topage);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			topage = ToMainPage.审核中.getValue();
			vp_task_main.setCurrentItem(topage);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_title_left:
				finish();
				break;
		case R.id.btn_task_nogoing:
			topage = ToMainPage.已通过.getValue();
			vp_task_main.setCurrentItem(topage);
			break;
		case R.id.btn_task_ongoing:
			topage = ToMainPage.审核中.getValue();
			vp_task_main.setCurrentItem(topage);
			break;
		case R.id.btn_task_finished:
			topage = ToMainPage.未通过.getValue();
			vp_task_main.setCurrentItem(topage);
			break;
		case R.id.btn_submit_taskTemple:
			showSubmitTaskTemple();
				break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//			Intent intent = new Intent(context, NoGoingTaskActicity.class);
//			startActivity(intent);
			finish();
		}
		return true;
	}

//	========================数值调整========================

	@Override
	public void showMyTaskMateriaCount(int num1, int num2, int num3) {
		layoutTopMenu.setShenhezhong(num1);
		layoutTopMenu.setYtongguo(num2);
		layoutTopMenu.setWeitongguo(num3);
	}

}
