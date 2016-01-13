package com.task.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.task.service.GetInvalidTaskAdapter;

/**
 * 过期失效任务fragment
 * 
 * @author llp
 * 
 */
public class FragmentInvalidTask extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener, INodata {
	private View view;
	private Context context;
	private ListView lv_task_main;
	private GetInvalidTaskAdapter getInvalidTaskAdapter;
	private PullToRefreshView pulltorefresh_taskList;
	private List<MyTaskContentBean> finishedTaskInfos;
	private String nextId = "";
	private int pageindex = 1;
	//private LayoutMyTaskTopmenu layoutTopMenu;// 顶部按钮
	public MyTaskFramentActivity mMyTaskListener;
	@SuppressLint("ValidFragment")
//	public FragmentInvalidTask(LayoutMyTaskTopmenu layoutTopMenu){
//		this.layoutTopMenu = layoutTopMenu;
//	}
	public FragmentInvalidTask(){

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

					FragmentInvalidTask.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSMyTask t) {
					FragmentInvalidTask.this.hideLayoutNoda();
					pulltorefresh_taskList.setVisibility(View.VISIBLE);
					mMyTaskListener.showMyTaskCount(t.data.passTotal,t.data.refuseTotal);
//					layoutTopMenu.setThroughNum(t.data.passTotal);
//					layoutTopMenu.setInvalid(t.data.refuseTotal);
					if (pageindex == 1) {
						if (t.data.count == 0) {
							pulltorefresh_taskList.setVisibility(View.GONE);
							FragmentInvalidTask.this.onNodata(
									ResultMsgType.Success, "刷新", "暂无过期任务！",
									FragmentInvalidTask.this);
						} else {
							finishedTaskInfos.clear();
							nextId = t.data.nextId;
							finishedTaskInfos.addAll(t.data.content);
							getInvalidTaskAdapter.notifyDataSetChanged();
						}
					} else {
						if (t.data.count == 0) {
							ToastUtil.show(context, "暂无更多数据");
						} else {
							nextId = t.data.nextId;
							finishedTaskInfos.addAll(t.data.content);
							getInvalidTaskAdapter.notifyDataSetChanged();
						}
					}
				}

				@Override
				public void onSericeErr(RSMyTask t) {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentInvalidTask.this.onNodata(
							ResultMsgType.ServiceErr, "刷新", "数据加载失败！", null);
				}

				@Override
				public void onSericeExp() {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentInvalidTask.this.onNodata(
							ResultMsgType.ServiceExp, "刷新", "数据加载失败！", null);

				}
			});
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		mMyTaskListener = (MyTaskFramentActivity) activity;
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
		getInvalidTaskAdapter = new GetInvalidTaskAdapter(context, finishedTaskInfos);
		lv_task_main.setAdapter(getInvalidTaskAdapter);
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
	
	@Override
	public void onResume() {
		super.onResume();
		getInitData();
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
				context, new RQMyTask(Utils.getUserDTO(context).data.userId, nextId,3),
				new RSMyTask(), ApiNames.获取所有已领取任务.getValue(),
				RequestType.POST, rqHandler_gqTask));
		pageindex++;
	}
}
