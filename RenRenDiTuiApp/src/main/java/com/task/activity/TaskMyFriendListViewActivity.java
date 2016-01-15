package com.task.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.TaskDeatailInfoNew;
import com.task.adapter.TaskFriendGridAdapter;

import base.BaseActivity;

/**
 * 任务参与人列表
 */
public class TaskMyFriendListViewActivity extends BaseActivity implements View.OnClickListener {
    private ListView mListView;
    private TaskDeatailInfoNew mData;
    private TaskFriendGridAdapter mTaskFriendGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_my_friend_list_view);
        mListView = (ListView)findViewById(R.id.lv_task_friend);
        mTaskFriendGridAdapter = new TaskFriendGridAdapter(context,1);
        mListView.setAdapter(mTaskFriendGridAdapter);
        mData = (TaskDeatailInfoNew)this.getIntent().getSerializableExtra("VO");
        mTaskFriendGridAdapter.setData(mData.partnerList);
        if(mIV_title_left!=null){
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setOnClickListener(this);
        }
        if(mTV_title_content!=null){
            mTV_title_content.setText("参与人列表");
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;

        }
    }
}
