package com.renrentui.tools;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.task.activity.TaskDetailInfoActivity;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 退出应用
 * 
 * @author back
 * 
 */
public class ExitApplication {

	private boolean isQuit = false; // 退出标识

	private List<Activity> activityList = new LinkedList<Activity>();

	private static ExitApplication instance;

	private ExitApplication() {
	}

	// 单例模式中获取唯一的ExitApplication 实例
	public static ExitApplication getInstance() {
		if (instance == null) {
			instance = new ExitApplication();
		}
		return instance;
	}

	// 添加Activity 到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity.finish 并退出该应用
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	// 遍历所有Activity.finish
	public void homeExit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	// 遍历所有Activity.finish
	public void lastExit() {
		activityList.get(activityList.size() - 2).finish();
	}

	// 再按一次返回键退出程序
	public boolean isExit(int keyCode, Context context) {
		String message = "再按一次返回键退出程序";
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				TimerTask task = new TimerTask() {

					@Override
					public void run() {
						isQuit = false;

					}
				};
				new Timer().schedule(task, 2000);
			} else {
				exit();
			}
		}
		return false;
	}
}
