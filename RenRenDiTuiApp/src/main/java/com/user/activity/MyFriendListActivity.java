package com.user.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.controls.PullToRefreshView;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQMyFriendsMode;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.MyFriendsBean;
import com.renrentui.resultmodel.RSMyFriendsMode;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.TimeUtils;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;

/**
 * 我的合伙人列表信息信息
 */
public class MyFriendListActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, INodata,
        View.OnClickListener {
    private ListView mListView;
    private MyFriendsAdapter mMyFriendsAdapter;

    private PullToRefreshView pulltorefresh_friendsList;// 上拉刷新，下拉加载
    private String userId;
    private String nextId = "0";
    private int pageindex = 1;
    private List<MyFriendsBean> data = new ArrayList<MyFriendsBean>();

    private TextView mTV_myfriens_num;//合伙人数量


    private RQHandler<RSMyFriendsMode> rqHandler_getFriendList = new RQHandler<>(
            new IRqHandlerMsg<RSMyFriendsMode>() {

                @Override
                public void onBefore() {
                    hideProgressDialog();
                    pulltorefresh_friendsList.onHeaderRefreshComplete();
                    pulltorefresh_friendsList.onFooterRefreshComplete();
                }

                @Override
                public void onNetworknotvalide() {
                    //网络无效
                    hideProgressDialog();
                    pulltorefresh_friendsList.setVisibility(View.GONE);
                    MyFriendListActivity.this.onNodata(
                            ResultMsgType.NetworkNotValide, 0, 0, "", null);

                }

                @Override
                public void onSuccess(RSMyFriendsMode t) {
                    hideProgressDialog();
                    MyFriendListActivity.this.hideLayoutNoda();
                    pulltorefresh_friendsList.setVisibility(View.VISIBLE);
                    mTV_myfriens_num.setVisibility(View.VISIBLE);
                    if (pageindex == 1) {
                        if (t.getData().getCount() == 0) {
                            pulltorefresh_friendsList.setVisibility(View.GONE);
                            MyFriendListActivity.this.onNodata(ResultMsgType.Success,0, R.string.every_no_data, "",
                                    MyFriendListActivity.this);
                        } else {
                            nextId = t.getData().getNextId();
                            if (data != null) {
                                data.clear();
                            } else {
                                data = new ArrayList<MyFriendsBean>();
                            }
                            data.addAll(t.getData().getContent());
                            mMyFriendsAdapter.setData(data);
                        }
                    } else {
                        if (t.getData().getCount() == 0) {
                            ToastUtil.show(context, "暂无更多数据");
                        } else {
                            nextId = t.getData().getNextId();
                            if (data == null) {
                                data = new ArrayList<MyFriendsBean>();
                            }
                            data.addAll(t.getData().getContent());
                            mMyFriendsAdapter.setData(data);
                        }
                    }
                    mTV_myfriens_num.setText("目前已有"+String.valueOf(data.size())+"个合伙人");
                }

                @Override
                public void onSericeErr(RSMyFriendsMode t) {
                    //服务器返回错误
                    hideProgressDialog();
                    pulltorefresh_friendsList.setVisibility(View.GONE);
                    MyFriendListActivity.this.onNodata(ResultMsgType.ServiceErr,
                            0, R.string.every_no_data_error, "", MyFriendListActivity.this);
                }

                @Override
                public void onSericeExp() {
                    //服务器返回为空
                    hideProgressDialog();
                    pulltorefresh_friendsList.setVisibility(View.GONE);
                    MyFriendListActivity.this.onNodata(ResultMsgType.ServiceErr,
                            0, R.string.every_no_data_error, "", MyFriendListActivity.this);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);
        super.init();
        mTV_myfriens_num =(TextView) findViewById(R.id.tv_myfriends_num);
        mTV_myfriens_num.setVisibility(View.GONE);
        pulltorefresh_friendsList = (PullToRefreshView) findViewById(R.id.pulltorefresh_friendslist);
        pulltorefresh_friendsList.setOnHeaderRefreshListener(this);
        pulltorefresh_friendsList.setOnFooterRefreshListener(this);
        mListView = (ListView) findViewById(R.id.lv_friends);
        mMyFriendsAdapter = new MyFriendsAdapter();
        mListView.setAdapter(mMyFriendsAdapter);
        userId = this.getIntent().getStringExtra("USER_ID");
        if (mIV_title_left != null) {
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setOnClickListener(this);
        }
        if (mTV_title_content != null) {
            mTV_title_content.setText("我的合伙人");
        }
        getTaskFriendsList();
    }

    /**
     * 获取数据
     */
    public void getTaskFriendsList() {
        showProgressDialog();
        pageindex = 1;
        nextId = "0";
        ApiUtil.Request(new RQBaseModel<RQMyFriendsMode, RSMyFriendsMode>(
                context, new RQMyFriendsMode(userId, nextId),
                new RSMyFriendsMode(), ApiNames.获取合伙人列表.getValue(),
                RequestType.POST, rqHandler_getFriendList));

    }

    /**
     * 获取数据
     */
    public void getMoreTaskFriendsList() {
        pageindex = pageindex + 1;
        ApiUtil.Request(new RQBaseModel<RQMyFriendsMode, RSMyFriendsMode>(
                context, new RQMyFriendsMode(userId, nextId),
                new RSMyFriendsMode(), ApiNames.获取合伙人列表.getValue(),
                RequestType.POST, rqHandler_getFriendList));

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

    //====================适配器==============================
    public class MyFriendsAdapter extends BaseAdapter {
        private List<MyFriendsBean> mData = new ArrayList<MyFriendsBean>();

        public void setData(List<MyFriendsBean> data) {
            if (mData == null) {
                mData = new ArrayList<MyFriendsBean>();
            } else {
                mData.clear();
            }
            mData.addAll(data);
            this.notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            if (mData == null) {
                return 0;
            } else {
                return mData.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HoldView mHolderView = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_myfriends_layout, parent, false);
                mHolderView = new HoldView(convertView);
                convertView.setTag(mHolderView);
            } else {
                mHolderView = (HoldView) convertView.getTag();
            }

            final MyFriendsBean bean = (MyFriendsBean) getItem(position);
            if (TextUtils.isEmpty(bean.getClienterName())) {
                mHolderView.mTV_1.setText("某推手");
            } else {
                mHolderView.mTV_1.setText(bean.getClienterName());
            }
            if (Util.IsNotNUll(bean.getHeadImage().trim()) && Utils.checkUrl(bean.getHeadImage().trim())) {
                ImageLoadManager.getLoaderInstace().disPlayNormalImg(bean.getHeadImage(), mHolderView.mIV_0,
                        R.drawable.icon_task_friend);
            } else {
                mHolderView.mIV_0.setImageResource(R.drawable.icon_task_friend);
            }
            mHolderView.mTV_2.setText("注册时间" + TimeUtils.StringPattern(bean.getCreateTime(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
            return convertView;
        }

        public class HoldView {
            private ImageView mIV_0;
            private TextView mTV_1;
            private TextView mTV_2;

            HoldView() {

            }

            HoldView(View view) {
                mIV_0 = (ImageView) view.findViewById(R.id.iv_task_friend_icon);
                mTV_1 = (TextView) view.findViewById(R.id.tv_task_friend_name);
                mTV_2 = (TextView) view.findViewById(R.id.tv_task_friend_time);
            }
        }

    }

}
