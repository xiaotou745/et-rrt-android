package com.task.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.renrentui.app.R;
import com.renrentui.controls.PullToRefreshView;
import com.renrentui.controls.PullToRefreshView.OnFooterRefreshListener;
import com.renrentui.controls.PullToRefreshView.OnHeaderRefreshListener;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQMyTask;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.MyTaskContentBean;
import com.renrentui.resultmodel.RSMyTask;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.adapter.MyFragmentTaskAdapter;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;

/**
 * 过期的任务 ---new
 * 
 * @author llp
 * 
 */
public class FragmentTask_1 extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener, INodata {
	private View view;
	private Context context;
	private ListView lv_task_main;
	private MyFragmentTaskAdapter taskAdapter_1;
	private PullToRefreshView pulltorefresh_taskList;
	private List<MyTaskContentBean> finishedTaskInfos;
	private String nextId = "";
	private int pageindex = 1;
	//private LayoutMyTaskTopmenu layoutTopMenu;// 顶部按钮
	public MyTaskFramentNewActivity mMyTaskListener;
	@SuppressLint("ValidFragment")
//	public FragmentInvalidTask(LayoutMyTaskTopmenu layoutTopMenu){
//		this.layoutTopMenu = layoutTopMenu;
//	}
	public FragmentTask_1(){

	}
	private RQHandler<RSMyTask> rqHandler_gqTask = new RQHandler<>(
			new IRqHandlerMsg<RSMyTask>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
					pulltorefresh_taskList.onHeaderRefreshComplete();
					pulltorefresh_taskList.onFooterRefreshComplete();
				}

				@Override
				public void onNetworknotvalide() {
					pulltorefresh_taskList.setVisibility(View.GONE);

					FragmentTask_1.this.onNodata(
							ResultMsgType.NetworkNotValide, R.drawable.icon_not_task, 0, "",null);
				}

				@Override
				public void onSuccess(RSMyTask t) {
					FragmentTask_1.this.hideLayoutNoda();
					pulltorefresh_taskList.setVisibility(View.VISIBLE);
					mMyTaskListener.showMyTaskCount(t.data.passTotal,t.data.refuseTotal);
//					layoutTopMenu.setThroughNum(t.data.passTotal);
//					layoutTopMenu.setInvalid(t.data.refuseTotal);
					if (pageindex == 1) {
						if (t.data.count == 0) {
							pulltorefresh_taskList.setVisibility(View.GONE);
							FragmentTask_1.this.onNodata(
									ResultMsgType.Success, R.drawable.icon_not_task,R.string.task_over_no_data,"",
									FragmentTask_1.this);
						} else {
							finishedTaskInfos.clear();
							nextId = t.data.nextId;
							finishedTaskInfos.addAll(t.data.content);
							taskAdapter_1.notifyDataSetChanged();
						}
					} else {
						if (t.data.count == 0) {
							ToastUtil.show(context, "暂无更多数据");
						} else {
							nextId = t.data.nextId;
							finishedTaskInfos.addAll(t.data.content);
							taskAdapter_1.notifyDataSetChanged();
						}
					}
				}

				@Override
				public void onSericeErr(RSMyTask t) {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentTask_1.this.onNodata(
							ResultMsgType.ServiceErr, R.drawable.icon_not_task,R.string.every_no_data_error, "", FragmentTask_1.this);
				}

				@Override
				public void onSericeExp() {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentTask_1.this.onNodata(
							ResultMsgType.ServiceErr, R.drawable.icon_not_task,R.string.every_no_data_error, "", FragmentTask_1.this);

				}
			});
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		mMyTaskListener = (MyTaskFramentNewActivity) activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_task_main, null);
		super.init(view);
		context = getActivity();
		pulltorefresh_taskList = (PullToRefreshView) view
				.findViewById(R.id.pulltorefresh_taskList);
		pulltorefresh_taskList.setOnHeaderRefreshListener(this);
		pulltorefresh_taskList.setOnFooterRefreshListener(this);
		lv_task_main = (ListView) view.findViewById(R.id.lv_task_main);
		lv_task_main.setOverScrollMode(View.OVER_SCROLL_NEVER);
		finishedTaskInfos = new ArrayList<MyTaskContentBean>();
		taskAdapter_1 = new MyFragmentTaskAdapter(context, finishedTaskInfos,1);
		lv_task_main.setAdapter(taskAdapter_1);
		return view;
	}

	/**
	 * 初始化数据
	 */
	public void getInitData() {
		ApiUtil.Request(new RQBaseModel<RQMyTask, RSMyTask>(
				context, new RQMyTask(Utils.getUserDTO(context).data.userId, "0",3),
				new RSMyTask(), ApiNames.获取所有已领取任务.getValue(),
				RequestType.POST, rqHandler_gqTask));
		pageindex = 1;
	}
	
//	@Override
//	public void onResume() {
//		super.onResume();
//		getInitData();
//	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		getInitData();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (getUserVisibleHint()) {

			showProgressDialog();
			getInitData();

		}
		super.onActivityCreated(savedInstanceState);
	}
	
	

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
		if (isVisibleToUser && isVisible()) {

			showProgressDialog();
			getInitData(); // 加载数据的方法

		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onNoData() {
		// TODO Auto-generated method stub
		showProgressDialog();
		getInitData();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		getMoreData();
	}

	/**
	 * 获取更多数据
	 */
	public void getMoreData() {
		ApiUtil.Request(new RQBaseModel<RQMyTask, RSMyTask>(
				context, new RQMyTask(Utils.getUserDTO(context).data.userId, nextId,3),
				new RSMyTask(), ApiNames.获取所有已领取任务.getValue(),
				RequestType.POST, rqHandler_gqTask));
		pageindex++;
	}
}
