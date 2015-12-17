/*
 * File Name: DownLoadDialog.java 
 * History:
 * Created by Administrator on 2015-7-17
 */
package com.user.model.download;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.renrentui.app.R;

/**
 * 更新提示框 (Description)
 * 
 * @author zaokafei
 * @version 1.0
 * @date 2015-7-17
 */
public class DownLoadDialog extends Dialog implements android.view.View.OnClickListener {
    private Context context;
    private TextView mTVContent;
    private TextView mTVTitle;
    private Button mBtnOK;
    private Button mBtnCancle;
    private String strVersion, strUrl, IsMust, content;

    public DownLoadDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected DownLoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public DownLoadDialog(Context context) {
        super(context);
        this.context = context;
    }

    public DownLoadDialog(Context context, String strVersion, String strUrl, String IsMust, String content) {
        super(context);
        this.context = context;
        this.strUrl = strUrl;
        this.strVersion = strVersion;
        this.IsMust = IsMust;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_dialog);
        initVIew();

    }

    private void initVIew() {
        mTVTitle = (TextView) findViewById(R.id.tv_dialog_title);
       // mTVTitle.setText(context.getResources().getString(R.string.download_title, strVersion));
        mTVTitle.setText("发现最新版本");
        mTVContent = (TextView) findViewById(R.id.download_update_content);
        mTVContent.setText(this.content);
        mBtnOK = (Button) findViewById(R.id.DL_update_id_ok);
        mBtnOK.setOnClickListener(this);
        mBtnCancle = (Button) findViewById(R.id.DL_update_id_cancel);
        mBtnCancle.setOnClickListener(this);
        if ("1".equals(IsMust.trim())) {
            mBtnCancle.setVisibility(View.GONE);
            this.setCancelable(false);
            this.setCanceledOnTouchOutside(false);
        } else {
            mBtnCancle.setVisibility(View.VISIBLE);
            this.setCancelable(true);
            this.setCanceledOnTouchOutside(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.DL_update_id_ok:
            Intent mServiceIntent = new Intent();
            mServiceIntent.setAction(DownLoadService.ACTION_DOWNLOADSERVICE_START);
            mServiceIntent.putExtra(DownLoadService.ACTION_EXTRA_URL_KEY, strUrl);
            context.startService(mServiceIntent);
            this.dismiss();
            break;
        case R.id.DL_update_id_cancel:
            this.dismiss();
            break;
        }
    }
}
