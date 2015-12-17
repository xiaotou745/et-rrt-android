package com.task.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.renrentui.app.R;
import com.renrentui.tools.QRcodeEncodingHandler;
import com.renrentui.util.Utils;

import base.BaseActivity;

/**
 * Created by Administrator on 2015/12/8 0008.
 *
 * 分享页面
 */
public class ShareViewActivity extends BaseActivity {
    public String Task_id = "";
    public String str_userId = "";
    public String str_shareContent = "";//分享信息

    public ImageView mSharePic;
    public Bitmap mBitmap;
    public  String str_scanTip;//扫码说明
    public String str_reminder;//温馨提示
    public TextView mTV_share_title;

    public TextView mTV_share_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_layout);
        super.init();
        Intent intent = getIntent();
        Task_id = intent.getStringExtra("TASK_ID");
        str_userId = Utils.getUserDTO(context).data.userId;
        str_shareContent = intent.getStringExtra("SHARE_CONTENT");
        mSharePic = (ImageView)findViewById(R.id.img_pic);
        mTV_share_title = (TextView)findViewById(R.id.tv_share_title);
        mTV_share_content = (TextView)findViewById(R.id.tv_share_content);
        str_scanTip = intent.getStringExtra("scanTip");
        str_reminder = intent.getStringExtra("reminder");
        try {
            mTV_share_title.setText(str_scanTip);
            mTV_share_content.setText(str_reminder);
            mBitmap = QRcodeEncodingHandler.createQRCode(str_shareContent, context.getResources()
                    .getDimensionPixelSize(R.dimen._300dp));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        mSharePic.setImageBitmap(mBitmap);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}
