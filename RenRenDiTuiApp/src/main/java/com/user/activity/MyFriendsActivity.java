package com.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQMyFriendModel;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSMyFriendModel;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.Utils;


import base.BaseActivity;

/**
 * 合伙人
 */
public class MyFriendsActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTV_friend_money;
    private TextView mTV_friend_num;
    private TextView mTV_friend_phone;


    private RQHandler<RSMyFriendModel> rqHandler_getMyFriend = new RQHandler<>(
            new IRqHandlerMsg<RSMyFriendModel>() {

                @Override
                public void onBefore() {
                }

                @Override
                public void onNetworknotvalide() {
hideProgressDialog();
                }

                @Override
                public void onSuccess(RSMyFriendModel t) {
                    hideProgressDialog();
                    if(t!=null){
                        mTV_friend_money.setText(t.data.getBonusTotal());
                        mTV_friend_num.setText(t.data.getPartnerNum());
                        mTV_friend_phone.setText(t.data.getRecommendPhone());
                    }

                }

                @Override
                public void onSericeErr(RSMyFriendModel t) {
                    hideProgressDialog();
                }

                @Override
                public void onSericeExp() {
                    hideProgressDialog();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        super.init();
        initControl();
        getMyFriendData();
    }
    private void initControl(){
        if(mIV_title_left!=null){
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setOnClickListener(this);
        }
        if(mTV_title_content!=null){
            mTV_title_content.setText("我的合伙人");
        }

        mTV_friend_money = (TextView)findViewById(R.id.tv_friend_money_01);
        mTV_friend_num = (TextView)findViewById(R.id.tv_friend_num);
        mTV_friend_phone = (TextView)findViewById(R.id.tv_friend_phone);

    }

    private void getMyFriendData(){
        //获取信息
            showProgressDialog();
            ApiUtil.Request(new RQBaseModel<RQMyFriendModel, RSMyFriendModel>(
                    context, new RQMyFriendModel(Utils.getUserDTO(context).data.userId),
                    new RSMyFriendModel(), ApiNames.获取合伙人分红信息.getValue(),
                    RequestType.POST, rqHandler_getMyFriend));

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
