package com.user.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.IBack;

public class HelpCenterActivity extends BaseActivity {

	private WebView help_center;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_center);
		super.init();

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
	
	@Override
	public void onBack(IBack iBack) {
		super.onBack(iBack);
	}

}
