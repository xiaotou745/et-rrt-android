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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import base.BaseFragment;
import base.BaseFragmentActivity;

import com.renrentui.app.MyApplication;
import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetMyMessage;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSGetMyMessage;
import com.renrentui.tools.ExitApplication;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToMyTaskPage;
import com.task.model.LayoutMyTaskTopmenu;
import com.task.service.MyFragmentPagerAdapter;
import com.user.activity.PersonalCenterActivity;

/**
 * Created by Administrator on 2015/12/1 0001.
 * 我的任务列表页
 */
public class MyTaskFramentActivity extends BaseFragmentActivity implements
        OnClickListener , BaseFragmentActivity.MyTaskInterface {
    private ViewPager vp_task_my;
    private MyFragmentPagerAdapter viewPagerAdapter;
    private List<BaseFragment> fragmentList;
    private FragmentThroughTask fragmentThroughTask;// 进行中的任务
    private FragmentInvalidTask fragmentInvalidTask;// 已过期任务
    private int topage = ToMyTaskPage.进行中.getValue();// intent指向要显示的页面
    private LayoutMyTaskTopmenu layoutTopMenu;// 顶部按钮

    //	bottom
    private LinearLayout mTab_01;
    private LinearLayout mTab_02;
    private LinearLayout mTab_03;
    private ImageView mTab_image_03;
    private TextView mTab_text_03;
    private boolean isQuit = false;


    private RQHandler<RSGetMyMessage> rqHandler_getMyMessage = new RQHandler<>(
            new IRqHandlerMsg<RSGetMyMessage>() {

                @Override
                public void onBefore() {
                }

                @Override
                public void onNetworknotvalide() {
                    //网络无效
                }

                @Override
                public void onSuccess(RSGetMyMessage t) {
                    if(t.getData()>0){
                        MyApplication.isMessage = true;
                        mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_2_image_layout);
                    }else{
                        //无信息
                        MyApplication.isMessage = false;
                        mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_1_image_layout);
                    }
                }

                @Override
                public void onSericeErr(RSGetMyMessage t) {
                }

                @Override
                public void onSericeExp() {

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_my);
        super.init();
        ExitApplication.getInstance().addActivity(this);
        initView();
        initViewPager(topage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyMessageInfo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            topage = intent.getIntExtra("to", ToMyTaskPage.进行中.getValue());
        }
        vp_task_my.setCurrentItem(topage);
    }
    /**
     * 获取未读信息
     */
    private void getMyMessageInfo(){
        ApiUtil.Request(new RQBaseModel<RQGetMyMessage, RSGetMyMessage>(
                context, new RQGetMyMessage(strUserId),
                new RSGetMyMessage(), ApiNames.获取未读信息数量.getValue(),
                RequestType.POST, rqHandler_getMyMessage));
    }
    private void initView() {
        if(mTV_title_content!=null){
            mTV_title_content.setText("我的任务");
        }
//		menu
        mTab_01 = (LinearLayout)findViewById(R.id.tab_01);
        mTab_01.setOnClickListener(this);
        mTab_02 = (LinearLayout)findViewById(R.id.tab_02);
        mTab_02.setOnClickListener(this);
        mTab_02.setSelected(true);
        mTab_03 = (LinearLayout)findViewById(R.id.tab_03);
        mTab_03.setOnClickListener(this);
        mTab_image_03 = (ImageView)findViewById(R.id.tab_image_03);
        if( MyApplication.isMessage){
            mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_1_image_layout);
        }else{
            mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_2_image_layout);
        }
        mTab_text_03 = (TextView)findViewById(R.id.tab_text_03);

        vp_task_my = (ViewPager) findViewById(R.id.vp_task_my);
        layoutTopMenu = new LayoutMyTaskTopmenu(context);
        layoutTopMenu.setOnClickListener(this);
    }

    private void initViewPager(int topage) {
        fragmentThroughTask = new FragmentThroughTask();
        fragmentInvalidTask = new FragmentInvalidTask();
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(fragmentThroughTask);
        fragmentList.add(fragmentInvalidTask);
        viewPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), context, fragmentList);
        vp_task_my.setAdapter(viewPagerAdapter);
        vp_task_my.setOffscreenPageLimit(0);
        vp_task_my.setOnPageChangeListener(new MyOnPageChangeListener());
        vp_task_my.setCurrentItem(topage);
        layoutTopMenu.selected(topage);
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
        Intent intent = null;
        boolean isFinish =false;
        switch (v.getId()) {
            case R.id.btn_task_through:
                intent = null;
                topage = ToMyTaskPage.进行中.getValue();
                break;
            case R.id.btn_task_invalid:
                intent = null;
                topage = ToMyTaskPage.已过期.getValue();
                break;
            case R.id.tab_01:
                //可接任务
                intent = new Intent(context, NoGoingTaskActicity.class);
                isFinish = true;
                break;
            case R.id.tab_02:
                //我的任务
                break;
            case R.id.tab_03:
                //个人中心
                    intent = new Intent(context, PersonalCenterActivity.class);
                    isFinish = true;
                break;
        }
        if(intent==null){
            vp_task_my.setCurrentItem(topage);
        }else{
            startActivity(intent);
            if(isFinish){
                finish();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击两次退出应用程序处理逻辑
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (!isQuit ) {
                isQuit = true;
                Toast.makeText(context, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                new Timer().schedule(task, 2000);
            } else {
                ExitApplication.getInstance().exit();
                MyTaskFramentActivity.this.finish();
                System.exit(0);
            }
        }
        return true;
    }
//===========数量更改===============

    @Override
    public void showMyTaskCount(String num1, String num2) {
        layoutTopMenu.setThroughNum(String.valueOf(num1));
        layoutTopMenu.setInvalid(String.valueOf(num2));
    }
}
