package com.renrentui.view.city;

import java.util.ArrayList;
import java.util.List;
import com.renrentui.app.R;
import com.renrentui.tools.Util;
import com.renrentui.util.UIHelper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 选择城市Dialog
 * @author llp
 *
 */
public class CityPickWheelDialog extends Dialog implements OnClickListener,
		OnWheelChangedListener {
	private Context mContext;
	private WheelView wvProvince;
	private WheelView wvCity;
	private WheelView wvArea;

	private Button btn_cancel;
	private Button btn_sure;

	/**
	 * 第二次点击时带回第一次点击的结果
	 */
	private CityMode cityMode;

	private CityButtonListener listener;
	private CityWorker cityWorker;
	private CityAdapter cityAdapter;
	private CityAdapter provinceAdapter;
	private CityAdapter areaAdapter;
	private List<CityMode> provinceList = new ArrayList<CityMode>();
	private List<CityMode> cityList = new ArrayList<CityMode>();
	private List<CityMode> areaList = new ArrayList<CityMode>();

	private enum adapterType {
		PROVINCE, CITY, AREA
	}

	public CityPickWheelDialog(Context context) {
		super(context);
		this.mContext = context;
		cityWorker = new CityWorker(context);
	}

	public CityPickWheelDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
		cityWorker = new CityWorker(context);
	}

	public CityPickWheelDialog(Context context, int theme, CityMode cityMode) {
		super(context, theme);
		this.mContext = context;
		cityWorker = new CityWorker(context);
		this.cityMode = cityMode;
	}

	public CityPickWheelDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.mContext = context;
		cityWorker = new CityWorker(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_city_pick);
		initView();
		setListener();
	}

	public CityPickWheelDialog setCityButtomListener(CityButtonListener listener) {
		this.listener = listener;
		return this;
	}

	private void initView() {
		wvProvince = (WheelView) findViewById(R.id.province_wheel);

		wvCity = (WheelView) findViewById(R.id.city_wheel);
		wvArea = (WheelView) findViewById(R.id.area_wheel);
		btn_sure = (Button) findViewById(R.id.btn_citypick_sure);
		btn_cancel = (Button) findViewById(R.id.btn_citypick_cancel);

		int dp_13 = UIHelper.dip2px(mContext, 14);
		wvProvince.TEXT_SIZE = dp_13;
		wvCity.TEXT_SIZE = dp_13;
		wvArea.TEXT_SIZE = dp_13;

		provinceList = cityWorker.getProvince();
		if (provinceList.size() > 0) {
			cityList = cityWorker.getCityByProvince(provinceList.get(0)
					.getCountryID());
			wvCity.setCanSrcoll(cityList.size() > 1 ? true : false);
		}
		if (cityList.size() > 0) {
			areaList = cityWorker.getAreaByCity(cityList.get(0).getCountryID());
			wvArea.setCanSrcoll(areaList.size() > 1 ? true : false);
		}
		cityAdapter = new CityAdapter(adapterType.CITY);
		provinceAdapter = new CityAdapter(adapterType.PROVINCE);
		areaAdapter = new CityAdapter(adapterType.AREA);

		wvProvince.setAdapter(provinceAdapter);
		wvCity.setAdapter(cityAdapter);
		wvArea.setAdapter(areaAdapter);
	}

	private void setListener() {
		wvProvince.addChangingListener(this);
		wvCity.addChangingListener(this);
		wvArea.addChangingListener(this);
		btn_cancel.setOnClickListener(this);
		btn_sure.setOnClickListener(this);

		// /////////////////////////////////////////
		if (cityMode != null) {
			wvProvince
					.setCurrentItem(Integer.parseInt(cityMode.getProvinceID()) - 1);
			wvCity.setCurrentItem(Integer.parseInt(cityMode.getCityID()));
			wvArea.setCurrentItem(Integer.parseInt(cityMode.getCountryID()));
		}
	}

	public String[] getAddress() {
		StringBuilder address = new StringBuilder();
		String province = null;
		String provinceCode = null;
		String city = null;
		String cityCode = null;
		String area = null;
		String areaCode = null;
		if (provinceList.size() > 0) {
			province = provinceList.get(wvProvince.getCurrentItem())
					.getAllName().trim();
			provinceCode = provinceList.get(wvProvince.getCurrentItem())
					.getCountryID().trim();
		}
		if (cityList.size() > 0) {
			city = cityList.get(wvCity.getCurrentItem()).getAllName().trim();
			cityCode = wvCity.getCurrentItem() + "";
		}
		if (areaList.size() > 0) {
			area = areaList.get(wvArea.getCurrentItem()).getAllName().trim();
			areaCode = wvArea.getCurrentItem() + "";
		}
		if (city.equals(province)) {
			address.append(province);
			if (Util.IsNotNUll(area)) {
				address.append(area);
			}
		} else {
			address.append(province);
			address.append(city);
			if (Util.IsNotNUll(area)) {
				address.append(area);
			}
		}
		return new String[] { address.toString(), provinceCode, cityCode,
				areaCode };
	}

	public String getCity() {
		String city = null;
		if (cityList.size() > 0) {
			city = cityList.get(wvCity.getCurrentItem()).getName();
		}
		return city;
	}

	public CityMode getArea() {
		CityMode area = null;
		if (areaList.size() > 0) {
			area = cityList.get(wvArea.getCurrentItem());
		}
		return area;
	}

	@Override
	public void dismiss() {
		super.dismiss();
		cityWorker.closeCityWorker();
	}

	@Override
	public void onClick(View v) {
		if (v == btn_sure) {
			listener.onPositiveButton(this);
		} else if (v == btn_cancel) {
			listener.onNegativeButton(this);
		}
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == wvProvince) {
			cityList = cityWorker.getCityByProvince(provinceList.get(newValue)
					.getCountryID());
			wvCity.setCanSrcoll(cityList.size() > 1 ? true : false);
			cityAdapter.notifiDataSetChange();
		} else if (wheel == wvCity) {
			if (newValue < cityList.size()) {
				areaList = cityWorker.getAreaByCity(cityList.get(newValue)
						.getCountryID());
				wvArea.setCanSrcoll(areaList.size() > 1 ? true : false);
				areaAdapter.notifiDataSetChange();
			}

		} else if (wheel == wvArea) {

		}
	}

	public interface CityButtonListener {
		void onPositiveButton(CityPickWheelDialog dialog);

		void onNegativeButton(CityPickWheelDialog dialog);
	}

	private class CityAdapter implements WheelAdapter {
		List<CityMode> currentList;
		adapterType type;

		public CityAdapter(adapterType type) {
			this.type = type;
			currentList = new ArrayList<>();
			if (type == adapterType.PROVINCE) {
				setCurrentList(provinceList);
			} else if (type == adapterType.CITY) {
				setCurrentList(cityList);
			} else if (type == adapterType.AREA) {
				setCurrentList(areaList);
			}
		}

		public void setCurrentList(List<CityMode> list) {
			for (CityMode c : list) {
				if (c.getName().length() > 5) {
					String s = c.getName().substring(0, 3);
					s = s + "...";
					c.setName(s);
				}
				currentList.add(c);
			}
		}

		public void notifiDataSetChange() {
			currentList = new ArrayList<>();
			if (type == adapterType.PROVINCE) {
				setCurrentList(provinceList);
				wvProvince.setAdapter(this);
			} else if (type == adapterType.CITY) {
				setCurrentList(cityList);
				wvCity.setAdapter(this);
			} else if (type == adapterType.AREA) {
				setCurrentList(areaList);
				wvArea.setAdapter(this);
			}
		}

		@Override
		public int getItemsCount() {
			return currentList.size() == 0 ? 1 : currentList.size();
		}

		@Override
		public String getItem(int index) {
			return currentList.size() == 0 ? null : currentList.get(index)
					.getName();
		}

		@Override
		public int getMaximumLength() {
			return currentList.size() == 0 ? 1 : currentList.size();
		}
	}

}
