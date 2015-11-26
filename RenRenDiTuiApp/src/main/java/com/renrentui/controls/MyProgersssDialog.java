package com.renrentui.controls;

import com.renrentui.app.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 加载中的Dialog
 * @author back
 *
 */
public class MyProgersssDialog extends Dialog {
	private Context context;
	private ImageView img;
	private TextView txt;
    private AnimationDrawable animationDrawable;

	public MyProgersssDialog(Context context) {
		super(context, R.style.progress_dialog);
		this.context = context;
		// 加载布局文件
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.progress_dialog, null);
		img = (ImageView) view.findViewById(R.id.progress_dialog_img);
		txt = (TextView) view.findViewById(R.id.progress_dialog_txt);
		// 给图片添加动态效果
//		Animation anim = AnimationUtils.loadAnimation(context,
//				R.anim.loading_dialog_progressbar);
//		img.setAnimation(anim);
        animationDrawable = (AnimationDrawable) img.getDrawable();  
        animationDrawable.start();
		txt.setText(R.string.progressbar_dialog_txt);
		// dialog添加视图
		setContentView(view);
	}

	public void setMsg(String msg) {
		txt.setText(msg);
	}

	public void setMsg(int msgId) {
		txt.setText(msgId);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
//		if (isShowing())
//			hide();
		return super.onKeyDown(keyCode, event);
	}

}