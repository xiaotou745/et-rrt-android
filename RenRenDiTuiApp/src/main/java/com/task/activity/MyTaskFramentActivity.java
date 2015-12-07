package com.task.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import base.BaseFragment;
import base.BaseFragmentActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.IBack;
import com.renrentui.tools.ExitApplication;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.ToMyTaskPage;
import com.task.model.LayoutMainTopmenu;
import com.task.model.LayoutMyTaskTopmenu;
import com.task.service.MyFragmentPagerAdapter;
import com.user.activity.PersonalCenterActivity;

/**
 * Created by Administrator on 2015/12/1 0001.
 * 我的任务列表页
 */
public class MyTaskFramentActivity extends BaseFragmentActivity implements
        OnClickListener {
    private Context context;
    private ViewPager vp_task_my;
    private MyFragmentPagerAdapter viewPagerAdapter;
    private List<BaseFragment> fragmentList;
    private FragmentThroughTask fragmentThroughTask;// 审核通过任务页
    private FragmentInvalidTask fragmentInvalidTask;// 过期失效任务页
    private FragmentCancelledTask fragmentCancelledTask;// 已取消任务页
    private int topage = ToMyTaskPage.审核通过.getValue();// intent指向要显示的页面
    private LayoutMyTaskTopmenu layoutTopMenu;// 顶部按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_my);
        super.init();
        ExitApplication.getInstance().addActivity(this);
        context = this;
        initView();
        initViewPager(topage);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            topage = intent.getIntExtra("to", ToMyTaskPage.审核通过.getValue());
        }
        vp_task_my.setCurrentItem(topage);
    }

    private void initView() {
        vp_task_my = (ViewPager) findViewById(R.id.vp_task_my);
        layoutTopMenu = new LayoutMyTaskTopmenu(context);
        layoutTopMenu.setOnClickListener(this);
    }

    private void initViewPager(int topage) {
        fragmentThroughTask = new FragmentThroughTask();
        fragmentInvalidTask = new FragmentInvalidTask();
        fragmentCancelledTask = new FragmentCancelledTask();
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(fragmentThroughTask);
        fragmentList.add(fragmentInvalidTask);
        fragmentList.add(fragmentCancelledTask);
        viewPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), context, fragmentList);
        vp_task_my.setAdapter(viewPagerAdapter);
        vp_task_my.setOffscreenPageLimit(0);
        vp_task_my.setOnPageChangeListener(new MyOnPageChangeListener());
        vp_task_my.setCurrentItem(topage);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_task_through:
                topage = ToMyTaskPage.审核通过.getValue();
                break;
            case R.id.btn_task_invalid:
                topage = ToMyTaskPage.过期失效.getValue();
                break;
            case R.id.btn_task_cancelled:
                topage = ToMyTaskPage.已取消.getValue();
                break;
        }
        vp_task_my.setCurrentItem(topage);
    }

}
