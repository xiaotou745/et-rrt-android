package com.user.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQReadMyMessage;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSReadMyMessage;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.TimeUtils;
import com.renrentui.util.Utils;


import base.BaseActivity;

/**
 * 消息详情
 */
public class MyMessageDetails extends BaseActivity implements View.OnClickListener {
    private TextView mTV_noticesMsg;
    private TextView strNoticesTime;
    private String strMessageId;
    private String strMessageTime;
    private String strMessageContent;


    //======================================
    private RQHandler<RSReadMyMessage> rqHandler_readMessage = new RQHandler<>(
            new IRqHandlerMsg<RSReadMyMessage>() {

                @Override
                public void onBefore() {
                }

                @Override
                public void onNetworknotvalide() {
                }

                @Override
                public void onSuccess(RSReadMyMessage t) {


                }

                @Override
                public void onSericeErr(RSReadMyMessage t) {
                }

                @Override
                public void onSericeExp() {

                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_details);
        initView();
        initViewData();
        getMyMessageData();
    }
    private void initView() {
        if(mIV_title_left!=null){
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setOnClickListener(this);
        }
        if(mTV_title_content!=null){
            mTV_title_content.setText("消息中心");
        }
        strMessageContent = this.getIntent().getStringExtra("content");
        strMessageId = this.getIntent().getStringExtra("id");
        strMessageTime = this.getIntent().getStringExtra("time");

        mTV_noticesMsg = (TextView) findViewById(R.id.tv_notices);
        strNoticesTime = (TextView) findViewById(R.id.tv_notices_time);
    }

    private void initViewData() {
        mTV_noticesMsg.setText(strMessageContent);
        if (TextUtils.isEmpty(strMessageTime)) {
            strNoticesTime.setVisibility(View.GONE);
        }
        strNoticesTime.setText(TimeUtils.StringPattern(strMessageTime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm"));
    }
    //获取信息
    private void getMyMessageData(){
        ApiUtil.Request(new RQBaseModel<RQReadMyMessage, RSReadMyMessage>(
                context, new RQReadMyMessage(Utils.getUserDTO(context).data.userId,strMessageId,"2"),
                new RSReadMyMessage(), ApiNames.删除或将消息已读.getValue(),
                RequestType.POST, rqHandler_readMessage));
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
