package com.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQGiveupTask;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.resultmodel.TaskMetarialContent;

import base.BaseActivity;
import com.renrentui.app.R;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;

/**
 * Created by Administrator on 2015/12/2 0002.
 *
 * 任务资料审核详情
 */
public class MyTaskMaterialDetailActivity extends BaseActivity implements
        View.OnClickListener {
    private RelativeLayout mRL_top;
    private View mView_top_line;
    private ImageView mIV_top_icon;
    private TextView mTV_top_content;
    private TextView mTV_top_time;


    private RelativeLayout mRL_middle;
    private View mView_middle_line;
    private ImageView mIV_middle_icon;
    private TextView mTV_middle_content;
    private TextView mTV_middle_time;

    private RelativeLayout mRL_bottom;
    private View mView_bottom_line;
    private TextView mTV_bottom_time;
    private TextView mTV_bottom_content;
    private ImageView mIV_bottom_icon;

    private Button mBtn_left;
    private Button mBtn_right;

    public TaskMetarialContent beanContent;//资料详情
    public String taskMaterialId ="";//资料id
    public String userId = "0";
    public String taskID;
    public String ctId="";//地推关系
    public String status  = "1";//资料状态 1：审核中 2：已通过  3：拒绝
    public String  titlt_content;//任务资料审核
    public int i_auditCycle;//周期
    public String str_taskStatus;//任务的状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_taskmaterial_status_layout);
        super.init();
        initControl();
        getDefaultValue();
        initControlValue();

    }

    private void getDefaultValue(){
        beanContent = (TaskMetarialContent)this.getIntent().getSerializableExtra("VO");
        taskMaterialId = this.getIntent().getStringExtra("TaskMaterialId");
        userId =this.getIntent().getStringExtra("UserId");
        taskID = this.getIntent().getStringExtra("TaskId");
        status = this.getIntent().getStringExtra("Status");
        titlt_content = this.getIntent().getStringExtra("Title_content");
        ctId = this.getIntent().getStringExtra("ctId");
        str_taskStatus = this.getIntent().getStringExtra("taskStatus");
        i_auditCycle = Integer.parseInt(beanContent.auditCycle);
    }
    /**
     * 初始化控件
     */
    private void initControl() {
        mRL_top = (RelativeLayout)findViewById(R.id.rl_flow_top);
        mIV_top_icon = (ImageView)findViewById(R.id.iv_top_icon);
        mView_top_line = findViewById(R.id.view_top_line);
        mTV_top_content = (TextView)findViewById(R.id.tv_task_material_top_content);
        mTV_top_time = (TextView)findViewById(R.id.tv_task_material_top_time);

        mRL_middle = (RelativeLayout)findViewById(R.id.rl_flow_middle);
        mIV_middle_icon = (ImageView)findViewById(R.id.iv_middle_icon);
        mView_middle_line = findViewById(R.id.view_middle_line);
        mTV_middle_content = (TextView)findViewById(R.id.tv_task_material_middle_content);
        mTV_middle_time = (TextView)findViewById(R.id.tv_task_material_middle_time);

        mRL_bottom = (RelativeLayout)findViewById(R.id.rl_flow_bottom);
        mIV_bottom_icon = (ImageView)findViewById(R.id.iv_bottom_icon);
        mView_bottom_line = findViewById(R.id.view_bottom_line);
        mTV_bottom_content = (TextView)findViewById(R.id.tv_task_material_bottom_content);
        mTV_bottom_time = (TextView)findViewById(R.id.tv_task_material_bottom_time);


        mBtn_left = (Button)findViewById(R.id.btn_left);
        mBtn_left.setOnClickListener(this);
        mBtn_right = (Button)findViewById(R.id.btn_right);
        mBtn_right.setOnClickListener(this);
    }
    private void initControlValue(){
        mtv_title_content.setText(titlt_content);
        if("1".equals(status)) {
            //审核中
            mRL_top.setVisibility(View.VISIBLE);
            mIV_top_icon.setImageResource(R.drawable.task_pass);
            mView_top_line.setBackgroundColor(context.getResources().getColor(R.color.blue_bg));
            mTV_top_content.setText("任务资料提交");
            mTV_top_time.setText(beanContent.createDate);

            mRL_middle.setVisibility(View.VISIBLE);
            mIV_middle_icon.setImageResource(R.drawable.task_wating);
            mView_middle_line.setBackgroundColor(context.getResources().getColor(R.color.yellow));
            mTV_middle_content.setText("任务资料审核中");
            mTV_middle_content.setTextColor(context.getResources().getColor(R.color.yellow));
            String str_auditCity = "审核周期";
            if (i_auditCycle <= 1) {
                str_auditCity = str_auditCity + "0-" + String.valueOf(i_auditCycle) + "天";
            } else {
                str_auditCity = str_auditCity + String.valueOf(i_auditCycle - 1) + "-" + String.valueOf(i_auditCycle) + "天";
            }
            mTV_middle_time.setText(str_auditCity);

            mRL_bottom.setVisibility(View.GONE);
            mBtn_left.setVisibility(View.VISIBLE);
            mBtn_right.setVisibility(View.VISIBLE);
            mBtn_left.setText("查看资料");
            mBtn_right.setText("再次提交");
        }else if("2".equals(status)){
                //通过
                mRL_top.setVisibility(View.VISIBLE);
                mIV_top_icon.setImageResource(R.drawable.task_pass);
                mView_top_line.setBackgroundColor(context.getResources().getColor(R.color.blue_bg));
                mTV_top_content.setText("任务资料提交");
                mTV_top_time.setText(beanContent.createDate);

                mRL_middle.setVisibility(View.GONE);

                mRL_bottom.setVisibility(View.VISIBLE);
                mIV_bottom_icon.setImageResource(R.drawable.task_pass);
                mView_bottom_line.setBackgroundColor(context.getResources().getColor(R.color.blue_bg));
                mTV_bottom_content.setText("审核通过");
                mTV_bottom_time.setText(beanContent.auditTime);
                mBtn_left.setVisibility(View.VISIBLE);
                mBtn_right.setVisibility(View.VISIBLE);
                mBtn_left.setText("查看资料");
                mBtn_right.setText("再次提交");
        }else if("3".equals(status)){
                //拒绝
                mRL_top.setVisibility(View.VISIBLE);
                mIV_top_icon.setImageResource(R.drawable.task_pass);
                mView_top_line.setBackgroundColor(context.getResources().getColor(R.color.blue_bg));
                mTV_top_content.setText("任务资料提交");
                mTV_top_time.setText(beanContent.createDate);

                mRL_middle.setVisibility(View.GONE);

                mRL_bottom.setVisibility(View.VISIBLE);
                mIV_bottom_icon.setImageResource(R.drawable.task_up_pass);
                mView_bottom_line.setBackgroundColor(context.getResources().getColor(R.color.red_bg));
                mTV_bottom_content.setText("审核未通过");
                mTV_bottom_time.setText(beanContent.auditTime);
                mBtn_left.setVisibility(View.VISIBLE);
                mBtn_right.setVisibility(View.VISIBLE);
                mBtn_left.setText("查看资料");
                mBtn_right.setText("再次提交");
        }
        if(!"1".equals(str_taskStatus)){
            //过期装填
            mBtn_right.setClickable(false);
            mBtn_right.setBackgroundColor(context.getResources().getColor(R.color.gray_color));
        }else{
            mBtn_right.setClickable(true);
        }
    }
//    查看资料
    private void showTaskMatetailDetail(){
        Intent mIntent = new Intent();
        mIntent.setClass(MyTaskMaterialDetailActivity.this,ShowTaskMatailDetailActivity.class);
        mIntent.putExtra("taskId", taskID);
        mIntent.putExtra("UserId", userId);
        mIntent.putExtra("taskDatumId",this.taskMaterialId);
        mIntent.putExtra("ctId",ctId);
        startActivity(mIntent);
        finish();
    }
    //   再次参与
    private void getTaskAgain(){
        //提交新资料
        Intent mIntent = new Intent();
        mIntent.setClass(MyTaskMaterialDetailActivity.this,TaskDatumSubmitActiviyt.class);
        mIntent.putExtra("taskId", taskID);
        mIntent.putExtra("UserId", userId);
        mIntent.putExtra("taskDatumId",this.taskMaterialId);
        mIntent.putExtra("ctId",ctId);
        startActivity(mIntent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_left:
                showTaskMatetailDetail();
                break;
            case R.id.btn_right:
                    getTaskAgain();
                break;
        }

    }
}
