package com.user.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.renrentui.app.R;
import com.renrentui.controls.PullToRefreshView;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQMyCaptailModel;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.RSMyCaptailModel;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.user.adapter.MyCaptailAdapter;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;

/**
 *
 * 我的资金明细 收入

 */
public class MyCapitalFramen_1 extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, INodata  {
    private List<RSMyCaptailModel.DataList> dataLists;
    private Context context;
    private PullToRefreshView pulltorefresh＿captailsist;
    private int pageindex = 0;
    private int  nextId = 0;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private MyCaptailAdapter mAdapter;


    private RQHandler<RSMyCaptailModel> rqHandler_getMyCaptial = new RQHandler<>(
            new IRqHandlerMsg<RSMyCaptailModel>() {

                @Override
                public void onBefore() {
                    hideProgressDialog();
                    pulltorefresh＿captailsist.onHeaderRefreshComplete();
                    pulltorefresh＿captailsist.onFooterRefreshComplete();
                }

                @Override
                public void onNetworknotvalide() {
                    hideProgressDialog();
                    pulltorefresh＿captailsist.setVisibility(View.GONE);
                    MyCapitalFramen_1.this.onNodata(
                            ResultMsgType.NetworkNotValide, R.drawable.icon_no_income, 0,"", null);
                }

                @Override
                public void onSuccess(RSMyCaptailModel t) {
                    hideProgressDialog();
                    MyCapitalFramen_1.this.hideLayoutNoda();
                    pulltorefresh＿captailsist.setVisibility(View.VISIBLE);
                    if (pageindex == 1) {
                        if (t.getData()==null || t.getData().getCount()<=0) {
                            pulltorefresh＿captailsist.setVisibility(View.GONE);
                            MyCapitalFramen_1.this.onNodata(
                                    ResultMsgType.Success,R.drawable.icon_no_income, R.string.fund_no_data,"",
                                    MyCapitalFramen_1.this);
                        } else {
                            dataLists.clear();
                            nextId = t.getData().getNextId();
                            dataLists.addAll(t.getData().getContent());
                            mAdapter.setData(dataLists);
                        }
                    } else {
                        if (t.getData()==null || t.getData().getCount()==0) {
                            ToastUtil.show(context, "暂无更多数据");
                        } else {
                            nextId = t.getData().getNextId();
                            dataLists.addAll(t.getData().getContent());
                            mAdapter.setData(dataLists);
                        }
                    }
                }

                @Override
                public void onSericeErr(RSMyCaptailModel t) {
                    hideProgressDialog();
                    pulltorefresh＿captailsist.setVisibility(View.GONE);
                    MyCapitalFramen_1.this.onNodata(
                            ResultMsgType.ServiceErr,R.drawable.icon_no_income,R.string.every_no_data_error,"", MyCapitalFramen_1.this);
                }

                @Override
                public void onSericeExp() {
                    hideProgressDialog();
                    pulltorefresh＿captailsist.setVisibility(View.GONE);
                    MyCapitalFramen_1.this.onNodata(
                            ResultMsgType.ServiceErr,R.drawable.icon_no_income,R.string.every_no_data_error,"", MyCapitalFramen_1.this);

                }
            });



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyCapitalFramen_1() {

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            context = activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Change Adapter to display your content
        mAdapter = new MyCaptailAdapter(context,0);
        dataLists = new ArrayList<RSMyCaptailModel.DataList>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_captail_1, container, false);
        super.init(view);
        pulltorefresh＿captailsist = (PullToRefreshView) view
                .findViewById(R.id.pulltorefresh_captailList);
        pulltorefresh＿captailsist.setOnHeaderRefreshListener(this);
        pulltorefresh＿captailsist.setOnFooterRefreshListener(this);
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.lv_captails_1);
        mListView.setAdapter(mAdapter);
        mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        return view;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e("11111111111111111", "11111111111111111");
//
//        showProgressDialog();
//         getInitData();
//    }


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


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        // 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
//        // 通过这两个判断，就可以知道什么时候去加载数据了
//        if (isVisibleToUser && isVisible()) {
//
//            getInitData(); // 加载数据的方法
//
//        }
//        super.setUserVisibleHint(isVisibleToUser);
//    }
    @Override
    public void onDetach() {
        super.onDetach();
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

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        getInitData();
    }


//=======================================================
    /**
     * 初始化数据
     */
    public void getInitData() {
        ApiUtil.Request(new RQBaseModel<RQMyCaptailModel, RSMyCaptailModel>(
                context, new RQMyCaptailModel(Utils.getUserDTO(context).data.userId,"1"),
                new RSMyCaptailModel(), ApiNames.获取资金明细列表.getValue(),
                RequestType.POST, rqHandler_getMyCaptial));
        pageindex = 1;
//        ApiUtil.Request(new RQBaseModel<RQMyCaptailModel, RSMyCaptailModel>(
//                context, new RQMyCaptailModel("22","1"),
//                new RSMyCaptailModel(), ApiNames.获取资金明细列表.getValue(),
//                RequestType.POST, rqHandler_getMyCaptial));
//        pageindex = 1;
    }

    /**
     * 初始化数据
     */
    public void getMoreData() {
        ApiUtil.Request(new RQBaseModel<RQMyCaptailModel, RSMyCaptailModel>(
                context, new RQMyCaptailModel(Utils.getUserDTO(context).data.userId,nextId,"1"),
                new RSMyCaptailModel(), ApiNames.获取资金明细列表.getValue(),
                RequestType.POST, rqHandler_getMyCaptial));
        pageindex++;
    }
}
