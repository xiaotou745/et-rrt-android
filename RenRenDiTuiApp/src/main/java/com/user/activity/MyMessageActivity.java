package com.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.renrentui.app.R;
import com.renrentui.controls.PullToRefreshView;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQMyMessage;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.MyMessageContentBean;
import com.renrentui.resultmodel.RSMyMessage;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.user.adapter.MyMessageAdapter;

import java.util.ArrayList;

import base.BaseActivity;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 我的消息中心
 */
public class MyMessageActivity extends BaseActivity implements
        PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, INodata {



    public ArrayList<MyMessageContentBean> myMessageList = new ArrayList<MyMessageContentBean>();
   public MyMessageAdapter myMessageAdapter ;
    private String nextId = "";
    private int pageindex = 1;
   public PullToRefreshView mPullMyMessageListView;
    public ListView mListView;

    //======================================
    private RQHandler<RSMyMessage> rqHandler_getMessage = new RQHandler<>(
            new IRqHandlerMsg<RSMyMessage>() {

                @Override
                public void onBefore() {
                    hideProgressDialog();
                    mPullMyMessageListView.onHeaderRefreshComplete();
                    mPullMyMessageListView.onFooterRefreshComplete();
                }

                @Override
                public void onNetworknotvalide() {
                    mPullMyMessageListView.setVisibility(View.GONE);
                    MyMessageActivity.this.onNodata(
                            ResultMsgType.NetworkNotValide, null, null, null);
                }

                @Override
                public void onSuccess(RSMyMessage t) {
                    MyMessageActivity.this.hideLayoutNoda();
                    mPullMyMessageListView.setVisibility(View.VISIBLE);
                    if (pageindex == 1) {
                        //第一页
                        if (t.data.content==null || t.data.content.size()==0) {
                            mPullMyMessageListView.setVisibility(View.GONE);
                            MyMessageActivity.this.onNodata(
                                    ResultMsgType.Success, "刷新", "暂无消息信息",
                                    MyMessageActivity.this);
                        } else {
                            if(myMessageList==null){
                                myMessageList =  new ArrayList<MyMessageContentBean>();
                            }
                            myMessageList.clear();
                            nextId = t.data.nextId;
                            myMessageList.addAll(t.data.content);
                            myMessageAdapter.setMessageData(myMessageList);
                        }
                    } else {
                        //多条数据
                        if (t.data.content==null || t.data.content.size()==0) {
                            ToastUtil.show(context, "暂无更多数据");
                        } else {
                            nextId = t.data.nextId;
                            myMessageList.addAll(t.data.content);
                            myMessageAdapter.setMessageData(myMessageList);
                        }
                    }
                }

                @Override
                public void onSericeErr(RSMyMessage t) {
                    mPullMyMessageListView.setVisibility(View.GONE);
                    MyMessageActivity.this.onNodata(
                            ResultMsgType.ServiceErr, "刷新", "信息加载失败！", null);
                }

                @Override
                public void onSericeExp() {
                    mPullMyMessageListView.setVisibility(View.GONE);
                    MyMessageActivity.this.onNodata(
                            ResultMsgType.ServiceExp, "刷新", "数据加载失败！", null);

                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage_layout);
        super.init();
        initView();
        getMyMessageData();
    }
    private void initView(){
        mPullMyMessageListView = (PullToRefreshView)findViewById(R.id.pulltorefresh_message_taskList);

        mListView = (ListView)findViewById(R.id.lv_message);
        myMessageAdapter = new MyMessageAdapter(context);
        mListView.setAdapter(myMessageAdapter);
        myMessageAdapter.setMessageData(myMessageList);
    }
    //获取信息
    private void getMyMessageData(){
        showProgressDialog();
        ApiUtil.Request(new RQBaseModel<RQMyMessage, RSMyMessage>(
                context, new RQMyMessage(Utils.getUserDTO(context).data.userId, "0"),
                new RSMyMessage(),ApiNames.获取消息列表.getValue(),
                RequestType.POST, rqHandler_getMessage));
        pageindex = 1;
    }
//获取更多
    private void getMoreMyMessageData(){
        ApiUtil.Request(new RQBaseModel<RQMyMessage, RSMyMessage>(
                context, new RQMyMessage(Utils.getUserDTO(context).data.userId, this.nextId),
                new RSMyMessage(),ApiNames.获取消息列表.getValue(),
                RequestType.POST, rqHandler_getMessage));
        pageindex ++;
    }


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        getMoreMyMessageData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        getMyMessageData();
    }

    @Override
    public void onNoData() {
        getMyMessageData();
    }

}
