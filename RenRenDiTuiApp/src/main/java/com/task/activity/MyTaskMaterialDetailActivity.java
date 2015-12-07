package com.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
 * 任务资料详情页
 */
public class MyTaskMaterialDetailActivity extends BaseActivity implements
        View.OnClickListener {
    private TextView mTV_top_content;
    private View mView_top_line;
    private TextView mTV_top_time;
    private ImageView mIV_top_icon;

    private TextView mTV_bottom_content;
    private View mView_bottom_line;
    private TextView mTV_bottom_time;
    private ImageView mIV_bottom_icon;

    private Button mBtn_left;
    private Button mBtn_right;

    public TaskMetarialContent beanContent;//资料详情
    public String taskMaterialId ="";//资料id
    public String userId = "0";
    public String taskID;
    public int status  = 1;//资料状态 1：审核中 2：已通过  3：拒绝
    public String  titlt_content;//任务资料审核


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
        status = this.getIntent().getIntExtra("Status",1);
        titlt_content = this.getIntent().getStringExtra("Title_content");
    }
    /**
     * 初始化控件
     */
    private void initControl() {
        mIV_top_icon = (ImageView)findViewById(R.id.iv_top_icon);
        mView_top_line = (View)findViewById(R.id.view_top_line);
        mTV_top_content = (TextView)findViewById(R.id.tv_task_material_top_content);
        mTV_top_time = (TextView)findViewById(R.id.tv_task_material_top_time);

        mIV_bottom_icon = (ImageView)findViewById(R.id.iv_bottom_icon);
        mView_bottom_line = (View)findViewById(R.id.view_bottom_line);
        mTV_bottom_content = (TextView)findViewById(R.id.tv_task_material_bottom_content);
        mTV_bottom_time = (TextView)findViewById(R.id.tv_task_material_bottom_time);


        mBtn_left = (Button)findViewById(R.id.btn_left);
        mBtn_left.setOnClickListener(this);
        mBtn_right = (Button)findViewById(R.id.btn_right);
        mBtn_right.setOnClickListener(this);
    }
    private void initControlValue(){
        mtv_title_content.setText(titlt_content);
        switch (status){
            case 1:
                mIV_top_icon.setImageResource(R.drawable.task_pass);
                mView_top_line.setBackgroundColor(context.getResources().getColor(R.color.blue_bg));
                mTV_top_content.setText("任务资料提交");
                mTV_top_time.setText(beanContent.createDate);
                mIV_bottom_icon.setVisibility(View.GONE);
                mView_bottom_line.setVisibility(View.GONE);
                mTV_bottom_content.setVisibility(View.GONE);
                mTV_bottom_time.setVisibility(View.GONE);
                mBtn_left.setVisibility(View.GONE);
                mBtn_right.setVisibility(View.GONE);
                break;
            case 2:
                //通过
                mIV_top_icon.setImageResource(R.drawable.task_pass);
                mView_top_line.setBackgroundColor(context.getResources().getColor(R.color.blue_bg));
                mTV_top_content.setText("任务资料提交");
                mTV_top_time.setText(beanContent.createDate);
                mIV_bottom_icon.setImageResource(R.drawable.task_pass);
                mView_bottom_line.setBackgroundColor(context.getResources().getColor(R.color.blue_bg));
                mTV_bottom_content.setText("审核通过");
                mTV_bottom_time.setText(beanContent.auditTime);
                mBtn_left.setVisibility(View.VISIBLE);
                mBtn_right.setVisibility(View.VISIBLE);
                mBtn_left.setText("查看资料");
                mBtn_right.setText("再次参与");
                break;
            case 3:
                mIV_top_icon.setImageResource(R.drawable.task_pass);
                mView_top_line.setBackgroundColor(context.getResources().getColor(R.color.blue_bg));
                mTV_top_content.setText("任务资料提交");
                mTV_top_time.setText(beanContent.createDate);
                mIV_bottom_icon.setImageResource(R.drawable.task_up_pass);
                mView_bottom_line.setBackgroundColor(context.getResources().getColor(R.color.red_bg));
                mTV_bottom_content.setText("审核未通过");
                mTV_bottom_time.setText(beanContent.auditTime);
                mBtn_left.setVisibility(View.VISIBLE);
                mBtn_right.setVisibility(View.VISIBLE);
                mBtn_left.setText("放弃任务");
                mBtn_right.setText("再次提交");
                break;
        }
    }
//    查看资料
    private void showTaskMatetailDetail(){
        taskMaterialId = this.getIntent().getStringExtra("TaskMaterialId");
        userId =this.getIntent().getStringExtra("UserId");
        taskID = this.getIntent().getStringExtra("TaskId");
        status = this.getIntent().getIntExtra("Status",1);

        Intent mIntent = new Intent();
        mIntent.setClass(MyTaskMaterialDetailActivity.this,ShowTaskMatailDetailActivity.class);
        mIntent.putExtra("taskId", taskID);
        mIntent.putExtra("UserId", userId);
        mIntent.putExtra("taskDatumId",this.taskMaterialId);
        startActivity(mIntent);
        finish();
    }
    //   再次参与
    private void getTaskAgain(){
        //提交新资料

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
