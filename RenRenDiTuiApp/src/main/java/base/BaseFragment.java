package base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.renrentui.app.R;
import com.renrentui.controls.MyProgersssDialog;
import com.renrentui.interfaces.IBack;
import com.renrentui.interfaces.INodata;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.tools.Util;

public class BaseFragment extends Fragment {
	private View layout_nodata;
	private Button btn_nodata;
	private TextView tv_nodata;
	private MyProgersssDialog progersssDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * 初始化基类设置 在子类setContentView之后调用
	 */
	public void init(View view) {
		layout_nodata = view.findViewById(R.id.layout_nodata);
		btn_nodata = (Button) view.findViewById(R.id.btn_nodata);
		tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);
		progersssDialog = new MyProgersssDialog(getActivity());
	}
	
	/**
	 * 页面没有数据时的统一处理
	 * 
	 * @param ResultMsgType
	 *            页面没有数据时的情况：网络无连接，数据加载失败，数据没有
	 * @param btnText
	 *            按钮上面显示的文字
	 * @param tvText
	 *            textview上面显示的文字
	 * @param iNodata
	 *            没有数据对应的后续操作
	 */
	public void onNodata(int resultMsgType, String btnText, String tvText,
			final INodata iNodata) {
		layout_nodata.setVisibility(View.VISIBLE);
		if (Util.IsNotNUll(tvText)) {
			tv_nodata.setText(tvText);
		} else {
			tv_nodata.setText("点击刷新页面！");
		}
		if (Util.IsNotNUll(btnText)) {
			btn_nodata.setText(btnText);
		} else {
			btn_nodata.setText("刷新");
		}
		if (iNodata != null) {
			btn_nodata.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					iNodata.onNoData();
				}
			});
		}
		switch (resultMsgType) {
		case ResultMsgType.NetworkNotValide:
			tv_nodata.setText("网络无连接！");
			btn_nodata.setText("设置");
			btn_nodata.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Settings.ACTION_SETTINGS);
					startActivity(intent);
				}
			});
			break;
		case ResultMsgType.ServiceErr:
			break;
		case ResultMsgType.ServiceExp:
			break;
		case ResultMsgType.Success:
			break;
		default:
			break;
		}
	}

	/**
	 * 页面有数据时隐藏没有数据的界面
	 */
	public void hideLayoutNoda() {
		layout_nodata.setVisibility(View.GONE);
	}

	/**
	 * 显示加载动画
	 */
	public void showProgressDialog() {
		if (getActivity().isFinishing()) {
			return;
		}
		if (progersssDialog == null) {
			progersssDialog = new MyProgersssDialog(getActivity());
		}
		if (!getActivity().isFinishing()) {
			progersssDialog.show();
		}
	}

	/**
	 * 隐藏加载动画
	 */
	public void hideProgressDialog() {
		if (getActivity().isFinishing()) {
			return;
		}
		if (progersssDialog != null) {
			progersssDialog.hide();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (progersssDialog != null) {
			progersssDialog.dismiss();
			progersssDialog = null;
		}
	}
}
