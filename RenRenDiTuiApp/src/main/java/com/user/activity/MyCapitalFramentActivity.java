package com.user.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.renrentui.app.R;
import com.renrentui.tools.ExitApplication;
import com.renrentui.util.ToMyCapitalPage;
import com.task.model.LayoutMyCapitalTopmenu;
import com.task.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import base.BaseFragmentActivity;

/**
 * 资金明细
 */
public class MyCapitalFramentActivity extends BaseFragmentActivity implements View.OnClickListener {


    private ViewPager vp_my_capital;
    private MyFragmentPagerAdapter viewPagerAdapter;
    private List<BaseFragment> fragmentList;
    private MyCapitalFramen_1 framen_1;// 收入
    private MyCapitalFramen_2 framen_2;// 支出
    private int topage = ToMyCapitalPage.收入.getValue();// intent指向要显示的页面
    private LayoutMyCapitalTopmenu layoutTopMenu;// 顶部按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_capital_frament);
        super.init();
        ExitApplication.getInstance().addActivity(this);
        initView();
        initViewPager(topage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.btn_capital_1:
                topage = ToMyCapitalPage.收入.getValue();
                vp_my_capital.setCurrentItem(topage);
                break;
            case R.id.btn_capital_2:
                topage = ToMyCapitalPage.支出.getValue();
                vp_my_capital.setCurrentItem(topage);
                break;

        }

    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

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
    ///-===============================================
    private void initView() {
        if(mIV_title_left!=null){
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setOnClickListener(this);
        }
        if(mTV_title_content!=null){
            mTV_title_content.setText("资金明细");
        }

        vp_my_capital = (ViewPager) findViewById(R.id.vp_capital_detail);
        layoutTopMenu = new LayoutMyCapitalTopmenu(context);
        layoutTopMenu.setOnClickListener(this);
    }

    private void initViewPager(int topage) {
        framen_1 = new MyCapitalFramen_1();
        framen_2 = new MyCapitalFramen_2();
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(framen_1);
        fragmentList.add(framen_2);
        viewPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), context, fragmentList);
        vp_my_capital.setAdapter(viewPagerAdapter);
        vp_my_capital.setOffscreenPageLimit(0);
        vp_my_capital.setOnPageChangeListener(new MyOnPageChangeListener());
        vp_my_capital.setCurrentItem(topage);
        layoutTopMenu.selected(topage);
    }

}
