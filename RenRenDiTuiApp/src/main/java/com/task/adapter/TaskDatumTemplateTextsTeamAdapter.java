package com.task.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.db.Bean.TaskTempleDBBean;
import com.renrentui.db.TaskTempleDBManager;
import com.renrentui.resultmodel.TaskDatumControlBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 任务资料模板中文字组适配器
 */
public class TaskDatumTemplateTextsTeamAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<TaskDatumControlBean> mData;
    private String str_userId;//用户id
    private String str_taskId;//任务id
    private String str_tag;
    private int iTeam_type;//组的类型
    private int iTeam_num;//组号
    private TaskTempleDBManager mTaskTempleManager;
    private int showContentType = 0;//0：是编辑 1：是显示//定义一个HashMap，用来存放EditText的值，Key是position

    /**
     *
     * @param con
     * @param data 单组数据集
     * @param tag  组的标签
     * @param iTeam_type 组的类型
     * @param iTeam_num  组号
     */
    public TaskDatumTemplateTextsTeamAdapter(Context con,ArrayList<TaskDatumControlBean> data,int iShowContentType,
                                             String userId,String taskId,String tag,int iTeam_type,int iTeam_num){
        mContext = con;
        showContentType = iShowContentType;
        mData= data;
        this.str_userId = userId;
        str_tag = tag;
        this.iTeam_type = iTeam_type;
        this.iTeam_num = iTeam_num;
        this.str_taskId = taskId;
        mTaskTempleManager = new TaskTempleDBManager(con);
        hashMap.clear();
    }

    public void setData(ArrayList<TaskDatumControlBean> data){
        hashMap.clear();
        if(mData==null){
            mData = new ArrayList<TaskDatumControlBean>();
        }else{
            mData.clear();
        }
        mData = data;
    }
    @Override
    public int getCount() {
        if(mData==null){
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private int index = -1;
   private HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HolderView mHolderView = null;
        final int position  = i;
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_team_texts_layout,viewGroup,false);
            mHolderView = new HolderView();
            mHolderView.mEitText = (EditText)view.findViewById(R.id.ed_team_texts_content_item);
            mHolderView.mTextView =(TextView) view.findViewById(R.id.tv_team_texts_content_item);
            view.setTag(mHolderView);
        }else {
            mHolderView = (HolderView)view.getTag();
        }
        final TaskDatumControlBean taskDean = (TaskDatumControlBean) getItem(i);
        if(showContentType==1){
            //展示数据
            mHolderView.mEitText.setVisibility(View.GONE);
            mHolderView.mTextView.setVisibility(View.VISIBLE);
            mHolderView.mTextView.setText(taskDean.controlValue);
        }else {
            //编辑数据
            mHolderView.mEitText.setVisibility(View.VISIBLE);
            mHolderView.mTextView.setVisibility(View.GONE);
            mHolderView.mEitText.setHint(taskDean.defaultValue);
            mHolderView.mEitText.setTag(str_tag + String.valueOf("_" + iTeam_num + "_") + String.valueOf(i));
            mHolderView.mEitText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                        index = position;
                    }
                    return false;
                }
            });
            mHolderView.mEitText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //将editText中保存到数据库中
                    String strContent = s.toString();
                    if(index!=-1 && index==position){
                        hashMap.put(position, strContent);
                        TaskTempleDBBean beanD = new TaskTempleDBBean();
                        beanD.setUSER_ID(str_userId);
                        beanD.setTASK_ID(str_taskId);
                        beanD.setTEAM_TYPE(String.valueOf(iTeam_type));
                        beanD.setTEAM_NUM(String.valueOf(iTeam_num));
                        beanD.setTEAM_NUM_INDEX(String.valueOf(position));
                        beanD.setTEAM_CONTENT_TYPE(taskDean.controlTypeId);
                        beanD.setTEAM_CONTENT_KEY(taskDean.controlKey);
                        beanD.setTEAM_CONTENT_VALUE(strContent);
                        mTaskTempleManager.updateOrAddTaskTemplate(beanD);
                    }
                }
            });
//            /如果hashMap不为空，就设置的editText
            if(hashMap.get(position) != null){
                index=-1;
                mHolderView.mEitText.setText(hashMap.get(position));
            }

        }
        Log.e("pppppppppppppp","pppppppp"+String.valueOf(position));
        return view;
    }
    public class HolderView{
        public EditText mEitText;
        public TextView mTextView;
    }

}
