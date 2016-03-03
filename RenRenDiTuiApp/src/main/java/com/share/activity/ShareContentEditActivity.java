package com.share.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.renrentui.app.R;
import com.renrentui.util.ToastUtil;
import com.share.bean.ShareBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import base.BaseActivity;

/**
 * 分享内容编辑页面
 */
public class ShareContentEditActivity extends BaseActivity implements View.OnClickListener{
    private Button mBtnShare;
    private EditText mShareContent;
    private String strShareType;
    private String strShareTypeContent;
    private ShareBean mShareBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_content_edit);
        super.init();

        strShareType = this.getIntent().getStringExtra("SHARE_TYPE");
        if(strShareType.equals("WEIXIN_CIRCLE")){
            //微信朋友圈
            strShareTypeContent="微信朋友圈";
        }else if(strShareType.equals("WEIXIN")){
            //微信
            strShareTypeContent="微信";
        }else if(strShareType.equals("QQ")){
            //qq
            strShareTypeContent="QQ";
        } else if(strShareType.equals("QZONE")){
            //qq 控件
            strShareTypeContent="QQ空间";
        }else  if(strShareType.equals("SINA")){
            //新浪微博
            strShareTypeContent="新浪微博";
        }
        mTV_title_content.setText("分享到"+strShareTypeContent);
        mShareBean = (ShareBean)this.getIntent().getSerializableExtra("SHARE_CONTENT");
        mBtnShare = (Button)findViewById(R.id.btn_share);
        mBtnShare.setOnClickListener(this);
        mShareContent = (EditText)findViewById(R.id.et_share_content);
        mShareContent.setText(mShareBean.getStrText());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share:
                submitShareContent();
                break;
            case R.id.iv_title_left:
                finish();
                break;
        }
    }
    private void submitShareContent(){
        String strContent = mShareContent.getText().toString().trim();
        if(TextUtils.isEmpty(strContent)){
            ToastUtil.show(context,"请填写分享内容");
            return;
        }
        ShareAction mShareAction =new ShareAction((Activity)context);
        if(strShareType.equals("WEIXIN_CIRCLE")){
            //微信朋友圈
                mShareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
        }else if(strShareType.equals("WEIXIN")){
            //微信
                mShareAction.setPlatform(SHARE_MEDIA.WEIXIN);
        }else if(strShareType.equals("QQ")){
            //qq
                mShareAction.setPlatform(SHARE_MEDIA.QQ);
        } else if(strShareType.equals("QZONE")){
            //qq 控件
                mShareAction.setPlatform(SHARE_MEDIA.QZONE);
        }else  if(strShareType.equals("SINA")){
            //新浪微博
            mShareAction.setPlatform(SHARE_MEDIA.SINA);
        }
        if(mShareBean!=null) {
            mShareAction.withTitle(mShareBean.getStrTitle());
            mShareAction.withFollow(mShareBean.getStrFollow());
            mShareAction.withTargetUrl(mShareBean.getStrTargetUrl());
            mShareAction.withText(mShareBean.getStrText());
            mShareAction.withMedia(mShareBean.getUmImage());
            mShareAction.withMedia(mShareBean.getUmVideo());
            mShareAction.withMedia(mShareBean.getuMusic());
            mShareAction.withMedia(new UMImage(context, "http://m.renrentui.me/img/144_qs.png"));
        }
        mShareAction.setListenerList(umShareListener);
        mShareAction.share();
    }
    /**
     * 分享结果监听
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
