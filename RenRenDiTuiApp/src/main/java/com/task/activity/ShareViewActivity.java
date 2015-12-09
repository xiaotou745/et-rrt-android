package com.task.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

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
        try {
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
