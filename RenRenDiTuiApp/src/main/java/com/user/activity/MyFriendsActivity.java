package com.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.share.ShareUtils;
import com.share.activity.ShareContentEditActivity;
import com.share.bean.ShareBean;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;


import org.w3c.dom.Text;

import base.BaseActivity;

/**
 * 合伙人
 */
public class MyFriendsActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTV_friend_money;
    private TextView mTV_friend_num;
    private TextView mTV_friend_phone;
    private TextView mTV_friend_show;//查看
    private Button mBtn_find_friend;//招募合伙人
    public int iFriends;//合伙人数量
    public String strphone;//推荐人手机号


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

                        if(TextUtils.isEmpty(t.data.getPartnerNum())){
                            mTV_friend_num.setText(" 0 人");
                        }else{
                            mTV_friend_num.setText(t.data.getPartnerNum()+" 人");
                            iFriends= Integer.parseInt(t.data.getPartnerNum());
                        }
                        if(iFriends<=0){
                            mTV_friend_show.setVisibility(View.INVISIBLE);
                        }else{
                            mTV_friend_show.setVisibility(View.VISIBLE);
                        }

                        if(!TextUtils.isEmpty(t.data.getRecommendPhone())){
                            strphone = t.data.getRecommendPhone();
                            StringBuffer sb = new StringBuffer();
                            sb.append(strphone.substring(0,3))
                                    .append("****")
                                    .append(strphone.substring(strphone.length()-4));
                            mTV_friend_phone.setText(sb.toString());
                        }else{
                            mTV_friend_phone.setText("无");
                        }

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
        mTV_friend_num = (TextView)findViewById(R.id.tv_f_2);
        mTV_friend_phone = (TextView)findViewById(R.id.tv_m_2);
        mBtn_find_friend = (Button)findViewById(R.id.btn_find_friend);
        mBtn_find_friend.setOnClickListener(this);
        mTV_friend_show = (TextView)findViewById(R.id.tv_f_3);
        mTV_friend_show.setOnClickListener(this);

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
            case R.id.tv_f_3:
                //查看
                if(iFriends>0) {
                    Intent mIntent = new Intent(context, MyFriendListActivity.class);
                    mIntent.putExtra("USER_ID", Utils.getUserDTO(context).data.userId);
                    context.startActivity(mIntent);
                }else {
                    ToastUtil.show(context,"暂无合伙人信息");
                }
                break;
            case R.id.btn_find_friend:
                showShareDisplay(strphone);
                break;
        }
    }
    /**
     * 招募合伙人
     */
    public void showShareDisplay(String stContent){
        ShareBean mShareBean = new ShareBean();
        mShareBean.setStrTitle("人人推－全民地推");
        mShareBean.setStrText("注册填写推荐人：" + stContent + "，成为我的合伙人，一起赚钱一起飞！");
        mShareBean.setStrTargetUrl("http://m.renrentui.me");
        ShareUtils mShareUtils = new ShareUtils(context,MyFriendsActivity.this,mShareBean);
        SHARE_MEDIA[] arrs =new SHARE_MEDIA[5];
        arrs[0] = SHARE_MEDIA.WEIXIN;
        arrs[1] =SHARE_MEDIA.WEIXIN_CIRCLE;
        arrs[2] = SHARE_MEDIA.QQ;
        arrs[3] = SHARE_MEDIA.QZONE;
        arrs[4] = SHARE_MEDIA.SINA;
        mShareUtils.showFindFriendsShareBoard(arrs, ShareContentEditActivity.class);
    }
}
