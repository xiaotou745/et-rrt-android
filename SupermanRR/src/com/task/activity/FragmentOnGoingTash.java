package com.task.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
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
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.FinishedTaskInfo;
import com.renrentui.resultmodel.RSGetFinishedTask;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.model.LayoutMainTopmenu;
import com.task.service.GetFinishedAdapter;
import com.task.service.GetOnGoingAdapter;

/**
 * 审核中任务fragment
 * 
 * @author llp
 * 
 */
public class FragmentOnGoingTash extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener, INodata {
	private View view;
	private Context context;
	private ListView lv_task_main;
	private GetOnGoingAdapter getOnGoingAdapter;
	private PullToRefreshView pulltorefresh_taskList;
	private List<FinishedTaskInfo> finishedTaskInfos;
	private String nextId = "";
	private int pageindex = 1;
	private LayoutMainTopmenu layoutTopMenu;// 顶部按钮

	public FragmentOnGoingTash(LayoutMainTopmenu layoutTopMenu){
		this.layoutTopMenu = layoutTopMenu;
	}

	public FragmentOnGoingTash() {
	}
	private RQHandler<RSGetFinishedTask> rqHandler_getOnGoingTask = new RQHandler<>(
			new IRqHandlerMsg<RSGetFinishedTask>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
					pulltorefresh_taskList.onHeaderRefreshComplete();
					pulltorefresh_taskList.onFooterRefreshComplete();
				}

				@Override
				public void onNetworknotvalide() {
					pulltorefresh_taskList.setVisibility(View.GONE);

					FragmentOnGoingTash.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSGetFinishedTask t) {
					FragmentOnGoingTash.this.hideLayoutNoda();
					layoutTopMenu.setLingqushu(t.data.receivedCount);
					layoutTopMenu.setShenheshu(t.data.passCount);
					layoutTopMenu.setWeitongguo(t.data.noPassCount);
					pulltorefresh_taskList.setVisibility(View.VISIBLE);
					if (pageindex == 1) {
						if (t.data.count == 0) {
							pulltorefresh_taskList.setVisibility(View.GONE);
							FragmentOnGoingTash.this.onNodata(
									ResultMsgType.Success, "刷新", "暂无已提交任务！",
									FragmentOnGoingTash.this);
						} else {
							finishedTaskInfos.clear();
							nextId = t.data.nextId;
							finishedTaskInfos.addAll(t.data.content);
							getOnGoingAdapter.notifyDataSetChanged();
						}
					} else {
						if (t.data.count == 0) {
							ToastUtil.show(context, "暂无更多数据");
						} else {
							nextId = t.data.nextId;
							finishedTaskInfos.addAll(t.data.content);
							getOnGoingAdapter.notifyDataSetChanged();
						}
					}
				}

				@Override
				public void onSericeErr(RSGetFinishedTask t) {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentOnGoingTash.this.onNodata(
							ResultMsgType.ServiceErr, "刷新", "数据加载失败！", null);
				}

				@Override
				public void onSericeExp() {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentOnGoingTash.this.onNodata(
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
		finishedTaskInfos = new ArrayList<FinishedTaskInfo>();
		getOnGoingAdapter = new GetOnGoingAdapter(context, finishedTaskInfos);
		lv_task_main.setAdapter(getOnGoingAdapter);
		return view;
	}

	/**
	 * 初始化数据
	 */
	public void getInitData() {
		ApiUtil.Request(new RQBaseModel<RQGetFinishedTask, RSGetFinishedTask>(
				context, new RQGetFinishedTask(Utils.getUserDTO(context).data.userId, "0",4),
				new RSGetFinishedTask(), ApiNames.获取所有已提交的任务.getValue(),
				RequestType.POST, rqHandler_getOnGoingTask));
		pageindex = 1;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getInitData();
	}

	// private RQGetNoGoingTask rqgetNoGoingTask;
	// private RSGetNoGoingTask rsgetNoGoingTask;

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
		ApiUtil.Request(new RQBaseModel<RQGetFinishedTask, RSGetFinishedTask>(
				context, new RQGetFinishedTask(Utils.getUserDTO(context).data.userId, nextId,4),
				new RSGetFinishedTask(), ApiNames.获取所有已提交的任务.getValue(),
				RequestType.POST, rqHandler_getOnGoingTask));
		pageindex++;
	}
}
