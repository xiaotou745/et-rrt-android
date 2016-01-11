package com.user.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.IBack;

public class HelpCenterActivity extends BaseActivity implements  View.OnClickListener{

	private WebView help_center;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_center);
		super.init();
		if(mIV_title_left!=null){
			mIV_title_left.setVisibility(View.VISIBLE);
			mIV_title_left.setOnClickListener(this);
		}
		if(mTV_title_content!=null){
			mTV_title_content.setText("帮助中心");
		}

		help_center = (WebView) findViewById(R.id.help_center);
		help_center.loadUrl("http://m.renrenditui.cn/htmls/help.html");
		help_center.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}


		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && help_center.canGoBack()) {
			help_center.goBack();// 返回前一个页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_title_left:
				if(help_center.canGoBack()){
					help_center.goBack();
				}else {
					finish();
				}
				break;
		}

	}
}
