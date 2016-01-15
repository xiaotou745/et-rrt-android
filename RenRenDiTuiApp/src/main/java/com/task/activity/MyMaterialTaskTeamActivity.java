package com.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.renrentui.app.R;
import com.renrentui.tools.ExitApplication;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.Utils;
import com.task.adapter.MyFragmentPagerAdapter;
import com.task.model.LayoutMainTopmenu;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import base.BaseFragmentActivity;

/*
  资料任务 分组
 */
public class MyMaterialTaskTeamActivity extends BaseFragmentActivity implements
		OnClickListener , BaseFragmentActivity.MyTaskMateriaInterface {
	public ViewPager vp_task_main;
	private MyFragmentPagerAdapter viewPagerAdapter;
	private List<BaseFragment> fragmentList;
	private FragmentMateriaTask_0 fragment_0;//审核中的资料
	private FragmentMateriaTask_1 fragment_1;//审核通过的资料
	private FragmentMateriaTask_2 fragment_2;// 未通过的资料
	private LayoutMainTopmenu layoutTopMenu;// 顶部按钮

	private int topage = ToMainPage.审核中.getValue();// intent指向要显示的页面

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_materia_task_layout);
		super.init();
		ExitApplication.getInstance().addActivity(this);
		initView();
		initViewPager(topage);

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private void initView() {
		if(mIV_title_left!=null){
			mIV_title_left.setVisibility(View.VISIBLE);
			mIV_title_left.setOnClickListener(this);
		}
		if(mTV_title_content!=null){
			mTV_title_content.setText("资料审核详情");
		}

		vp_task_main = (ViewPager) findViewById(R.id.vp_task_main);
		layoutTopMenu = new LayoutMainTopmenu(context);
		layoutTopMenu.setOnClickListener(this);
	}

	public void initViewPager(int index) {
		fragment_0 = new FragmentMateriaTask_0();
		fragment_1 = new FragmentMateriaTask_1();
		fragment_2 = new FragmentMateriaTask_2();
		fragmentList = new ArrayList<BaseFragment>();
		fragmentList.add(0,fragment_0);
		fragmentList.add(1,fragment_1);
		fragmentList.add(2,fragment_2);
		viewPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), context, fragmentList);
		vp_task_main.setAdapter(viewPagerAdapter);
		vp_task_main.setOffscreenPageLimit(0);
		vp_task_main.setOnPageChangeListener(new MyOnPageChangeListener());
		vp_task_main.setCurrentItem(index);
		layoutTopMenu.selected(index);
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

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
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
