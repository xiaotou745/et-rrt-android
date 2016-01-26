package com.task.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.renrentui.app.R;
import com.renrentui.controls.PullToRefreshView;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetCityMode;
import com.renrentui.requestmodel.RQGetTaskFriendList;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.MyTaskContentBean;
import com.renrentui.resultmodel.PartnerList;
import com.renrentui.resultmodel.RSGetCityModel;
import com.renrentui.resultmodel.RSGetTaskFriendsList;
import com.renrentui.resultmodel.TaskDeatailInfoNew;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.task.adapter.TaskFriendGridAdapter;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;

/**
 * 任务参与人列表
 */
public class TaskMyFriendListViewActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, INodata,
        View.OnClickListener {
    private ListView mListView;
    private TaskDeatailInfoNew mData;
    private TaskFriendGridAdapter mTaskFriendGridAdapter;

    private PullToRefreshView pulltorefresh_task_friendsList;// 上拉刷新，下拉加载
    private String str_taskId;
    private String nextId="0";
    private int pageindex = 1;
    private List<PartnerList> data = new ArrayList<PartnerList>();


    private RQHandler<RSGetTaskFriendsList> rqHandler_getTaskFriendList = new RQHandler<>(
            new IRqHandlerMsg<RSGetTaskFriendsList>() {

                @Override
                public void onBefore() {
                    hideProgressDialog();
                    pulltorefresh_task_friendsList.onHeaderRefreshComplete();
                    pulltorefresh_task_friendsList.onFooterRefreshComplete();
                }

                @Override
                public void onNetworknotvalide() {
                    //网络无效
                    hideProgressDialog();
                    pulltorefresh_task_friendsList.setVisibility(View.GONE);
                    TaskMyFriendListViewActivity.this.onNodata(
                            ResultMsgType.NetworkNotValide, null, null, null);

                }

                @Override
                public void onSuccess(RSGetTaskFriendsList t) {
                    hideProgressDialog();
                    TaskMyFriendListViewActivity.this.hideLayoutNoda();
                    pulltorefresh_task_friendsList.setVisibility(View.VISIBLE);
                    if (pageindex == 1) {
                        if (t.getData().getCount() == 0) {
                            pulltorefresh_task_friendsList.setVisibility(View.GONE);
                            TaskMyFriendListViewActivity.this.onNodata(
                                    ResultMsgType.Success, "刷新", "",
                                    TaskMyFriendListViewActivity.this);
                        } else {
                            nextId = t.getData().getNextId();
                            if(data!=null){
                                data.clear();
                            }else{
                                data = new ArrayList<PartnerList>();
                            }
                            data.addAll(t.getData().getContent());
                            mTaskFriendGridAdapter.setData(data);
                        }
                    } else {
                        if (t.getData().getCount() == 0) {
                            ToastUtil.show(context, "暂无更多数据");
                        } else {
                            nextId = t.getData().getNextId();
                            if(data==null){
                                data = new ArrayList<PartnerList>();
                            }
                            data.addAll(t.getData().getContent());
                            mTaskFriendGridAdapter.setData(data);
                        }
                    }


                }

                @Override
                public void onSericeErr(RSGetTaskFriendsList t) {
                    //服务器返回错误
                    hideProgressDialog();
                    pulltorefresh_task_friendsList.setVisibility(View.GONE);
                    TaskMyFriendListViewActivity.this.onNodata(ResultMsgType.ServiceErr,
                            "刷新", "数据加载失败！", null);
                }

                @Override
                public void onSericeExp() {
                    //服务器返回为空
                    hideProgressDialog();
                    pulltorefresh_task_friendsList.setVisibility(View.GONE);
                    TaskMyFriendListViewActivity.this.onNodata(ResultMsgType.ServiceErr,
                            "刷新", "数据加载失败！", null);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_my_friend_list_view);
        super.init();
        pulltorefresh_task_friendsList = (PullToRefreshView)findViewById(R.id.pulltorefresh_task_friendslist);
        pulltorefresh_task_friendsList.setOnHeaderRefreshListener(this);
        pulltorefresh_task_friendsList.setOnFooterRefreshListener(this);
        mListView = (ListView)findViewById(R.id.lv_task_friend);
        mTaskFriendGridAdapter = new TaskFriendGridAdapter(context,1);
        mListView.setAdapter(mTaskFriendGridAdapter);
      //  mData = (TaskDeatailInfoNew)this.getIntent().getSerializableExtra("VO");
        str_taskId = this.getIntent().getStringExtra("TASK_ID");
        if(mIV_title_left!=null){
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setOnClickListener(this);
        }
        if(mTV_title_content!=null){
            mTV_title_content.setText("参与人列表");
        }
        getTaskFriendsList();
    }

    /**
     * 获取数据
     */
    public void getTaskFriendsList() {
        showProgressDialog();
        pageindex=1;
        ApiUtil.Request(new RQBaseModel<RQGetTaskFriendList, RSGetTaskFriendsList>(
                context, new RQGetTaskFriendList(str_taskId,"0"),
                new RSGetTaskFriendsList(), ApiNames.获取任务参与人列表.getValue(),
                RequestType.POST, rqHandler_getTaskFriendList));

    }
    /**
     * 获取数据
     */
    public void getMoreTaskFriendsList() {
        pageindex= pageindex+1;
        ApiUtil.Request(new RQBaseModel<RQGetTaskFriendList, RSGetTaskFriendsList>(
                context, new RQGetTaskFriendList(str_taskId,nextId),
                new RSGetTaskFriendsList(), ApiNames.获取任务参与人列表.getValue(),
                RequestType.POST, rqHandler_getTaskFriendList));

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;

        }
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        getTaskFriendsList();
    }

    @Override
    public void onNoData() {
        getTaskFriendsList();
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        getMoreTaskFriendsList();
    }
}
