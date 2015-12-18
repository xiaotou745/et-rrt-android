package com.task.activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.renrentui.app.MyApplication;
import com.renrentui.app.R;
import com.renrentui.db.Bean.CityDBBean;
import com.renrentui.db.CityDBManager;
import com.renrentui.resultmodel.CityRegionModel;
import com.renrentui.util.ToastUtil;
import com.renrentui.view.MyLetterListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import base.BaseActivity;

/**
 * Created by Administrator on 2015/12/11 0011.
 * 城市信息展示
 */
public class ShowCityActivity extends BaseActivity implements AbsListView. OnScrollListener, View.OnClickListener {

    public CityDBManager mCityDBManager = null;//城市数据库
    private BaseAdapter adapter;
    private ResultListAdapter resultListAdapter;
    private ListView personList;
    private ListView resultList;
    private TextView overlay; // 对话框首字母textview
    private MyLetterListView letterListView; // A-Z listview
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;// 存放存在的汉语拼音首字母
    private Handler handler;
    private OverlayThread overlayThread; // 显示首字母对话框
    private ArrayList<CityDBBean> allCity_lists; // 所有城市列表
    private ArrayList<CityDBBean> city_lists;// 城市列表
    private ArrayList<CityDBBean> city_hot;//热门城市
    private ArrayList<CityDBBean> city_result;//搜索结果
    private ArrayList<CityDBBean> city_history;//历史城市
    private EditText sh;
    private TextView tv_noresult;

    private String currentCity; // 用于保存定位到的城市
    private boolean isNeedFresh;
    private boolean isLocation = false;
    public MyLocationListenner myListener = new MyLocationListenner();
    public Handler mShwoLocationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_layout);
        init();
        mCityDBManager = new CityDBManager(context);
        personList = (ListView) findViewById(R.id.list_view);
        allCity_lists = new ArrayList<CityDBBean>();
        city_hot = new ArrayList<CityDBBean>();
        city_result = new ArrayList<CityDBBean>();
        city_history = new ArrayList<CityDBBean>();
        resultList = (ListView) findViewById(R.id.search_result);
        sh = (EditText) findViewById(R.id.sh);
        tv_noresult = (TextView) findViewById(R.id.tv_noresult);
        sh.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() == null || "".equals(s.toString())) {
                    letterListView.setVisibility(View.VISIBLE);
                    personList.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                    tv_noresult.setVisibility(View.GONE);
                } else {
                    city_result.clear();
                    letterListView.setVisibility(View.GONE);
                    personList.setVisibility(View.GONE);
                    getResultCityList(s.toString());
                    if (city_result.size() <= 0) {
                        tv_noresult.setVisibility(View.VISIBLE);
                        resultList.setVisibility(View.GONE);
                    } else {
                        tv_noresult.setVisibility(View.GONE);
                        resultList.setVisibility(View.VISIBLE);
                        resultListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView01);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        isNeedFresh = true;
        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 3) {
                    //全部城市列表
                    String strName= allCity_lists.get(position).getCITY_NAME();
                    String strCode = allCity_lists.get(position).getCITY_CODE();
                    CityRegionModel bean = new CityRegionModel();
                    bean.code=strCode;
                    bean.name = strName;
                    MyApplication.setmCurrentLocation(bean);
                    ShowCityActivity.this.finish();
                }
            }
        });
        personList.setAdapter(adapter);
        personList.setOnScrollListener(this);
        resultListAdapter = new ResultListAdapter(this, city_result);
        resultList.setAdapter(resultListAdapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), city_result.get(position).getCITY_NAME()+"2222", Toast.LENGTH_SHORT).show();
            }
        });
             initOverlay();
        //本地数据展示
        cityInit();
        hotCityInit();
        hisCityInit();
        setAdapter(allCity_lists, city_hot, city_history);
        initLocation();
        mShwoLocationHandler   = new Handler() {
            public void handleMessage (Message msg){
                adapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(handler!=null){
            handler.removeCallbacks(overlayThread);
        }
        mMyApplication.getmLocClient().stop();
    }

    private boolean mReady;

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    //    初始化城市
    private void cityInit() {
        allCity_lists.clear();
        CityDBBean city = new CityDBBean("定位", "0"); // 当前定位城市
        allCity_lists.add(city);
//        city = new CityDBBean("最近", "1"); // 最近访问的城市
//        allCity_lists.add(city);
        city = new CityDBBean("热门", "1"); // 热门城市
        allCity_lists.add(city);
        city = new CityDBBean("全部", "2"); // 全部城市
        allCity_lists.add(city);
        city_lists = getCityList();
        allCity_lists.addAll(city_lists);
    }

    //    获取全部城市信息
    private ArrayList<CityDBBean> getCityList() {
//        列表
        ArrayList<CityDBBean> list = new ArrayList<CityDBBean>();
        list = mCityDBManager.getAllHotCity("3");
        Collections.sort(list, comparator);
        return list;
    }

    /**
     * 热门城市
     */
    public void hotCityInit() {
        //热门
        city_hot.clear();
        city_hot = mCityDBManager.getAllHotCity("2");
        Collections.sort(city_hot, comparator);

    }

    /**
     * 历史城市(当前城市)
     */
    private void hisCityInit() {
        //历史信息
        city_history.clear();
        city_history = mCityDBManager.getAllHotCity("4");
        Collections.sort(city_history, comparator);
    }

    private void setAdapter(List<CityDBBean> list, List<CityDBBean> hotList, List<CityDBBean> hisCity) {
        adapter = new ListAdapter(this, list, hotList, hisCity);
        personList.setAdapter(adapter);
    }

    private void getResultCityList(String keyword) {
        city_result = mCityDBManager.getKeyCityByName(keyword);
        Collections.sort(city_result, comparator);
    }

    private boolean isScroll = false;

    private class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                personList.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    //    ================================适配器======================================
    public class ListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<CityDBBean> list;
        private List<CityDBBean> hotList;
        private List<CityDBBean> hisCity;
        final int VIEW_TYPE = 5;

        public ListAdapter(Context context, List<CityDBBean> list, List<CityDBBean> hotList, List<CityDBBean> hisCity) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
            this.hotList = hotList;
            this.hisCity = hisCity;
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(list.get(i).getCITY_FIRST_LETTER());
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1).getCITY_FIRST_LETTER()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(list.get(i).getCITY_FIRST_LETTER());
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }
        }

        public void setCityData(List<CityDBBean> list, List<CityDBBean> hotList, List<CityDBBean> hisCity) {
            this.list = list;
            this.hotList = hotList;
            this.hisCity = hisCity;
            sections = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(list.get(i).getCITY_FIRST_LETTER());
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1).getCITY_FIRST_LETTER()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(list.get(i).getCITY_FIRST_LETTER());
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }
            this.notifyDataSetChanged();
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int position) {
            return position < 3 ? position : 3;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        ViewHolder holder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView city;
            int viewType = getItemViewType(position);
            if (viewType == 0) {
                // 定位
                convertView = inflater.inflate(R.layout.frist_list_item, parent, false);
                city = (TextView) convertView.findViewById(R.id.city);
                TextView title = (TextView) convertView.findViewById(R.id.recentHint);
                // city.setText(MyApplication.getmLocalLocation().name);
                title.setText("定位城市");

                if(isLocation){
                    //定位成功
                    city.setText(MyApplication.getmLocalLocation().name);
                    title.setClickable(true);
                }else{
                    city.setText("定位中...");
                    title.setClickable(false);
                }
                title.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                        MyApplication.setmCurrentLocation(MyApplication.getmLocalLocation());
                        ShowCityActivity.this.finish();
                    }
                });
            }
//            else if (viewType == 1) {
//                // 最近城市（历史）
//                convertView = inflater.inflate(R.layout.recent_city, parent, false);
//                GridView rencentCity = (GridView) convertView.findViewById(R.id.recent_city);
//                rencentCity.setAdapter(new HisCityAdapter(context, this.hisCity));
////                rencentCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                    @Override
////                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////
////                    }
////                });
//                TextView recentHint = (TextView) convertView.findViewById(R.id.recentHint);
//                recentHint.setText("最近访问的城市");
//            }
            else if (viewType == 1) {
                //热门城市
                convertView = inflater.inflate(R.layout.recent_city, parent, false);
                GridView hotCity = (GridView) convertView.findViewById(R.id.recent_city);
                hotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String strName= hotList.get(position).getCITY_NAME();
                        String strCode = hotList.get(position).getCITY_CODE();
                        CityRegionModel bean = new CityRegionModel();
                        bean.code=strCode;
                        bean.name = strName;
                        MyApplication.setmCurrentLocation(bean);
                        ShowCityActivity.this.finish();
                    }
                });
                hotCity.setAdapter(new HotCityAdapter(context, this.hotList));
                TextView hotHint = (TextView) convertView.findViewById(R.id.recentHint);
                hotHint.setText("热门城市");
            } else if (viewType == 2) {
                convertView = inflater.inflate(R.layout.total_item, parent, false);
            } else {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.list_item, parent, false);
                    holder = new ViewHolder();
                    holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
                    holder.name = (TextView) convertView.findViewById(R.id.name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if (position >= 1) {
                    holder.name.setText(list.get(position).getCITY_NAME());
                    String currentStr = getAlpha(list.get(position).getCITY_FIRST_LETTER());
                    String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getCITY_FIRST_LETTER()) : " ";
                    if (!previewStr.equals(currentStr)) {
                        holder.alpha.setVisibility(View.VISIBLE);
                        holder.alpha.setText(currentStr);
                    } else {
                        holder.alpha.setVisibility(View.GONE);
                    }
                }
            }
            return convertView;
        }

        private class ViewHolder {
            TextView alpha; // 首字母标题
            TextView name; // 城市名字
        }
    }

    //历史城市
    class HisCityAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<CityDBBean> hisCitys;

        public HisCityAdapter(Context context, List<CityDBBean> hisCitys) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
            this.hisCitys = hisCitys;
        }

        @Override
        public int getCount() {
            return hisCitys.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.item_city, null);
            TextView city = (TextView) convertView.findViewById(R.id.city);
            city.setText(hisCitys.get(position).getCITY_NAME());
            return convertView;
        }
    }

    //热门城市
    class HotCityAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<CityDBBean> hotCitys;

        public HotCityAdapter(Context context, List<CityDBBean> hotCitys) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
            this.hotCitys = hotCitys;
        }

        @Override
        public int getCount() {
            return hotCitys.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.item_city, null);
            TextView city = (TextView) convertView.findViewById(R.id.city);
            city.setText(hotCitys.get(position).getCITY_NAME());
            return convertView;
        }
    }

    //   搜索结果适配器
    private class ResultListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<CityDBBean> results = new ArrayList<CityDBBean>();

        public ResultListAdapter(Context context, ArrayList<CityDBBean> results) {
            inflater = LayoutInflater.from(context);
            this.results = results;
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(results.get(position).getCITY_NAME());
            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<CityDBBean>() {
        @Override
        public int compare(CityDBBean lhs, CityDBBean rhs) {
            return lhs.getCITY_FIRST_LETTER().compareTo(rhs.getCITY_FIRST_LETTER());
        }
    };

    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return "定位";
        } else if (str.equals("1")) {
            return "当前";
        } else if (str.equals("2")) {
            return "热门";
        } else if (str.equals("3")) {
            return "全部";
        } else {
            return "#";
        }
    }

    //    ==========================
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String text;
            String name = allCity_lists.get(firstVisibleItem).getCITY_NAME();
            String pinyin = allCity_lists.get(firstVisibleItem).getCITY_FIRST_LETTER();
//            if (firstVisibleItem < 4) {
//                text = name;
//            } else {
//                text = PingYinUtil.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
//            }
            text = name;
            overlay.setText(text);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟一秒后执行，让overlay为不可见
            handler.postDelayed(overlayThread, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                ShowCityActivity.this.finish();
                break;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myListener!=null){
            mMyApplication.getmLocClient().unRegisterLocationListener(myListener);
        }
    }
    //==============================定位========================
    /**
     * 初始化定位
     */
    public void initLocation() {
        try {
            mMyApplication.getmLocClient().registerLocationListener(myListener);
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true);// 开启gps
            option.setCoorType("bd09ll");// 返回百度经纬度坐标
            option.setIsNeedAddress(true);
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            option.setScanSpan(1000);
            mMyApplication.getmLocClient().setLocOption(option);
            mMyApplication.getmLocClient().start();
        }catch (Exception e){

        }finally {

        }
    }
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if(location!=null && !TextUtils.isEmpty(location.getCity())){
                //定位成功
                isLocation  =true;
                mMyApplication.getmLocClient().stop();
                CityRegionModel localLocation = new CityRegionModel();
                String StrName = location.getCity();
                String strCode = mCityDBManager.getCityCodeByName(StrName);
                localLocation.code = strCode;
                localLocation.name = StrName;
                MyApplication.setmLocalLocation(localLocation);
                mShwoLocationHandler.sendEmptyMessageDelayed(0,2*1000);
            }else{
                //定位失败
                mMyApplication.getmLocClient().stop();
            }
        }

    }
}
