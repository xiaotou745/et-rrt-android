package com.task.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import base.BaseFragment;

import com.renrentui.app.R;
import com.renrentui.controls.PullToRefreshView;
import com.renrentui.controls.PullToRefreshView.OnFooterRefreshListener;
import com.renrentui.controls.PullToRefreshView.OnHeaderRefreshListener;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetFinishedTask;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQMyTask;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.FinishedTaskInfo;
import com.renrentui.resultmodel.MyTaskContentBean;
import com.renrentui.resultmodel.RSGetFinishedTask;
import com.renrentui.resultmodel.RSMyTask;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.model.LayoutMyTaskTopmenu;
import com.task.service.GetThroughTaskAdapter;

/**
 * 进行中的任务
 * 
 * @author llp
 * 
 */
public class FragmentThroughTask extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener, INodata {
	private View view;
	private Context context;
	private ListView lv_task_main;
	private GetThroughTaskAdapter getInvalidTaskAdapter;
	private PullToRefreshView pulltorefresh_taskList;
	private List<MyTaskContentBean> finishedTaskInfos;
	private String nextId = "";
	private int pageindex = 1;
	private LayoutMyTaskTopmenu layoutTopMenu;// 顶部按钮

	@SuppressLint("ValidFragment")
	public FragmentThroughTask(LayoutMyTaskTopmenu layoutTopMenu){
		this.layoutTopMenu = layoutTopMenu;
	}



	private RQHandler<RSMyTask> rqHandler_jxzTask = new RQHandler<>(
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
					FragmentThroughTask.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSMyTask t) {
					FragmentThroughTask.this.hideLayoutNoda();
					layoutTopMenu.setThroughNum(t.data.passTotal);
					layoutTopMenu.setInvalid(t.data.refuseTotal);
					pulltorefresh_taskList.setVisibility(View.VISIBLE);
					if (pageindex == 1) {
						if (t.data.count == 0) {
							pulltorefresh_taskList.setVisibility(View.GONE);
							FragmentThroughTask.this.onNodata(
									ResultMsgType.Success, "刷新", "您还没有领取任务",
									FragmentThroughTask.this);
						} else {
							nextId = t.data.nextId;
							if(finishedTaskInfos!=null){
								finishedTaskInfos.clear();
							}else{
								finishedTaskInfos = new ArrayList<MyTaskContentBean>();
							}
							finishedTaskInfos.addAll(t.data.content);
							getInvalidTaskAdapter.setThroughTaskData(finishedTaskInfos);
						}
					} else {
						if (t.data.count == 0) {
							ToastUtil.show(context, "暂无更多数据");
						} else {
							nextId = t.data.nextId;
							if(finishedTaskInfos==null){
								finishedTaskInfos = new ArrayList<MyTaskContentBean>();
							}
							finishedTaskInfos.addAll(t.data.content);
							getInvalidTaskAdapter.setThroughTaskData(finishedTaskInfos);
						}
					}
				}

				@Override
				public void onSericeErr(RSMyTask t) {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentThroughTask.this.onNodata(
							ResultMsgType.ServiceErr, "刷新", "数据加载失败！", null);
				}

				@Override
				public void onSericeExp() {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentThroughTask.this.onNodata(
							ResultMsgType.ServiceExp, "刷新", "数据加载失败！", null);

				}
			});

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
		getInvalidTaskAdapter = new GetThroughTaskAdapter(context, finishedTaskInfos);
		lv_task_main.setAdapter(getInvalidTaskAdapter);
		return view;
	}

	/**
	 * 初始化数据
	 */
	public void getInitData() {
		pageindex = 1;
		ApiUtil.Request(new RQBaseModel<RQMyTask, RSMyTask>(
				context, new RQMyTask(Utils.getUserDTO(context).data.userId, "0",1),
				new RSMyTask(), ApiNames.获取所有已领取任务.getValue(),
				RequestType.POST, rqHandler_jxzTask));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//getInitData();
	}

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
				context, new RQMyTask(Utils.getUserDTO(context).data.userId, nextId,1),
				new RSMyTask(), ApiNames.获取所有已领取任务.getValue(),
				RequestType.POST, rqHandler_jxzTask));
		pageindex++;
	}
}
