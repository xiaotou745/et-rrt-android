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
import com.renrentui.requestmodel.RQGetOnGoingTask;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.NoGoingTaskInfo;
import com.renrentui.resultmodel.OnGoingTaskInfo;
import com.renrentui.resultmodel.RSGetNoGoingTask;
import com.renrentui.resultmodel.RSGetOnGoingTask;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.model.LayoutMainTopmenu;
import com.task.service.GetGoingAdapter;
import com.task.service.GetNoGoingAdapter;

/**
 * 已领取任务fragment
 * 
 * @author llp
 * 
 */
public class FragmentNoGoingTask extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener, INodata {

	private View view;
	private Context context;
	private ListView lv_task_main;
	private GetGoingAdapter getGoingAdapter;
	private PullToRefreshView pulltorefresh_taskList;
	private List<OnGoingTaskInfo> noGoingTaskInfos;
	private String nextId = "";
	private int pageindex = 1;
	private LayoutMainTopmenu layoutTopMenu;// 顶部按钮

	public FragmentNoGoingTask(LayoutMainTopmenu layoutTopMenu) {
		this.layoutTopMenu = layoutTopMenu;
	}
	public FragmentNoGoingTask() {
	}

	private RQHandler<RSGetOnGoingTask> rqHandler_getNoGoingTask = new RQHandler<>(
			new IRqHandlerMsg<RSGetOnGoingTask>() {

				@Override
				public void onBefore() {
					// TODO Auto-generated method stub
					hideProgressDialog();
					pulltorefresh_taskList.onHeaderRefreshComplete();
					pulltorefresh_taskList.onFooterRefreshComplete();
				}

				@Override
				public void onNetworknotvalide() {
					// TODO Auto-generated method stub
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentNoGoingTask.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSGetOnGoingTask t) {
					FragmentNoGoingTask.this.hideLayoutNoda();
					layoutTopMenu.setLingqushu(t.data.receivedCount);
					layoutTopMenu.setShenheshu(t.data.passCount);
					layoutTopMenu.setWeitongguo(t.data.noPassCount);
					pulltorefresh_taskList.setVisibility(View.VISIBLE);
					if (pageindex == 1) {
						if (t.data.count == 0) {
							pulltorefresh_taskList.setVisibility(View.GONE);
							FragmentNoGoingTask.this.onNodata(
									ResultMsgType.Success, "刷新", "暂无待领取任务！",
									FragmentNoGoingTask.this);
						} else {
							noGoingTaskInfos.clear();
							nextId = t.data.nextID;
							noGoingTaskInfos.addAll(t.data.content);
							getGoingAdapter.notifyDataSetChanged();

						}
					} else {
						if (t.data.count == 0) {
							ToastUtil.show(context, "暂无更多数据");
						} else {
							if (nextId == null) {
								ToastUtil.show(context, "暂无更多数据");
							} else {
								nextId = t.data.nextID;
								noGoingTaskInfos.addAll(t.data.content);
								getGoingAdapter.notifyDataSetChanged();
							}
						}
					}
				}

				@Override
				public void onSericeErr(RSGetOnGoingTask t) {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentNoGoingTask.this.onNodata(ResultMsgType.ServiceErr,
							"刷新", "数据加载失败！", null);
				}

				@Override
				public void onSericeExp() {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentNoGoingTask.this.onNodata(ResultMsgType.ServiceExp,
							"刷新", "数据加载失败！", null);
				}
			});

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_task_main, null);
		context = getActivity();
		super.init(view);
		pulltorefresh_taskList = (PullToRefreshView) view
				.findViewById(R.id.pulltorefresh_taskList);
		pulltorefresh_taskList.setOnHeaderRefreshListener(this);
		pulltorefresh_taskList.setOnFooterRefreshListener(this);
		lv_task_main = (ListView) view.findViewById(R.id.lv_task_main);
		lv_task_main.setOverScrollMode(View.OVER_SCROLL_NEVER);
		noGoingTaskInfos = new ArrayList<OnGoingTaskInfo>();
		getGoingAdapter = new GetGoingAdapter(context, noGoingTaskInfos);
		lv_task_main.setAdapter(getGoingAdapter);
		return view;
	}

	/**
	 * 获取数据
	 */
	public void getInitData() {
		ApiUtil.Request(new RQBaseModel<RQGetOnGoingTask, RSGetOnGoingTask>(
				context, new RQGetOnGoingTask(
						Utils.getUserDTO(context).data.userId, "0"),
				new RSGetOnGoingTask(), ApiNames.获取所有已领取任务.getValue(),
				RequestType.POST, rqHandler_getNoGoingTask));
		pageindex = 1;
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		getInitData();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
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
		showProgressDialog();
		getInitData();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		getMoreData();
	}

	@Override
	public void onResume() {
		super.onResume();
		getInitData();
	}

	/**
	 * 获取更多数据
	 */
	private void getMoreData() {
		ApiUtil.Request(new RQBaseModel<RQGetOnGoingTask, RSGetOnGoingTask>(
				context, new RQGetOnGoingTask(
						Utils.getUserDTO(context).data.userId, nextId),
				new RSGetOnGoingTask(), ApiNames.获取所有已领取任务.getValue(),
				RequestType.POST, rqHandler_getNoGoingTask));
		pageindex++;
	}

}
