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
import com.renrentui.requestmodel.RQMyMaterialTaskTeamModel;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.RSMyMaterialTaskTeamModel;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.adapter.MateriaTaskTeamAdapter;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;

/**
 * 资料任务审核 分组 --通过
 */
public class FragmentMateriaTask_1 extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener, INodata {
	private View view;
	private Context context;
	private ListView lv_task_main;
	private MateriaTaskTeamAdapter mTeamAdapter;
	private PullToRefreshView pulltorefresh_taskList;
	private List<RSMyMaterialTaskTeamModel.Content> taskMetarialContents;
	private String nextId = "";
	private int pageindex = 1;
	public MyMaterialTaskTeamActivity myTaskMaterialListener;

	@SuppressLint("ValidFragment")
	public FragmentMateriaTask_1(){

	}
	private RQHandler<RSMyMaterialTaskTeamModel> rqHandler_teamTask = new RQHandler<>(
			new IRqHandlerMsg<RSMyMaterialTaskTeamModel>() {
				@Override
				public void onBefore() {
					hideProgressDialog();
					pulltorefresh_taskList.onHeaderRefreshComplete();
					pulltorefresh_taskList.onFooterRefreshComplete();
				}

				@Override
				public void onNetworknotvalide() {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentMateriaTask_1.this.onNodata(
							ResultMsgType.NetworkNotValide, 0, 0,"", null);
				}

				@Override
				public void onSuccess(RSMyMaterialTaskTeamModel t) {
					FragmentMateriaTask_1.this.hideLayoutNoda();
					myTaskMaterialListener.showMyTaskMateriaCount(t.getData().getWaitTotal(),t.getData().getPassTotal(),t.getData().getRefuseTotal(),true);
					pulltorefresh_taskList.setVisibility(View.VISIBLE);
					if (pageindex == 1) {
						if (t.getData().getCount() == 0) {
							pulltorefresh_taskList.setVisibility(View.GONE);
							FragmentMateriaTask_1.this.onNodata(
									ResultMsgType.Success, 0,R.string.material_finish_no_data, "",
									FragmentMateriaTask_1.this);
						} else {
							taskMetarialContents.clear();
							nextId = t.getData().getNextId();
							taskMetarialContents.addAll(t.getData().getContent());
                            mTeamAdapter.setData(taskMetarialContents);
						}
					} else {
						if (t.getData().getCount() == 0) {
							ToastUtil.show(context, "暂无更多数据");
						} else {
							nextId = t.getData().getNextId();
							taskMetarialContents.addAll(t.getData().getContent());
                            mTeamAdapter.setData(taskMetarialContents);
						}
					}
				}

				@Override
				public void onSericeErr(RSMyMaterialTaskTeamModel t) {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentMateriaTask_1.this.onNodata(
							ResultMsgType.ServiceErr, 0,R.string.every_no_data_error, "", FragmentMateriaTask_1.this);
				}

				@Override
				public void onSericeExp() {
					pulltorefresh_taskList.setVisibility(View.GONE);
					FragmentMateriaTask_1.this.onNodata(
							ResultMsgType.ServiceErr, 0,R.string.every_no_data_error, "", FragmentMateriaTask_1.this);

				}
			});
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		myTaskMaterialListener = (MyMaterialTaskTeamActivity) activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_materiatask_team_layout, null);
		super.init(view);
		pulltorefresh_taskList = (PullToRefreshView) view
				.findViewById(R.id.pulltorefresh_taskteamList);
		pulltorefresh_taskList.setOnHeaderRefreshListener(this);
		pulltorefresh_taskList.setOnFooterRefreshListener(this);
		lv_task_main = (ListView) view.findViewById(R.id.lv_task_team_main);
		lv_task_main.setOverScrollMode(View.OVER_SCROLL_NEVER);
		taskMetarialContents = new ArrayList<RSMyMaterialTaskTeamModel.Content>();
		mTeamAdapter = new MateriaTaskTeamAdapter(context,1);
		lv_task_main.setAdapter(mTeamAdapter);
		return view;
	}

	/**
	 * 初始化数据
	 */
	public void getInitData() {
		pageindex = 1;
		ApiUtil.Request(new RQBaseModel<RQMyMaterialTaskTeamModel, RSMyMaterialTaskTeamModel>(
				context, new RQMyMaterialTaskTeamModel(Utils.getUserDTO(context).data.userId,"0","10","2"),
				new RSMyMaterialTaskTeamModel(), ApiNames.资料审核详情分组后列表.getValue(),
				RequestType.POST, rqHandler_teamTask));
	}
//
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
		showProgressDialog();
		getInitData();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		getMoreData();
	}

	/**
	 * 获取更多数据
	 */
	public void getMoreData() {
        ApiUtil.Request(new RQBaseModel<RQMyMaterialTaskTeamModel, RSMyMaterialTaskTeamModel>(
                context, new RQMyMaterialTaskTeamModel(Utils.getUserDTO(context).data.userId,nextId,"10","2"),
                new RSMyMaterialTaskTeamModel(), ApiNames.资料审核详情分组后列表.getValue(),
                RequestType.POST, rqHandler_teamTask));
		pageindex++;
	}
}
