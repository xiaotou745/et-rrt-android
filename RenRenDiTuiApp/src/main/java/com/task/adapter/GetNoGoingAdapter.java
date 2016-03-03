package com.task.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.NoGoingTaskInfo;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.UIHelper;
import com.renrentui.util.Utils;
import com.task.activity.TaskDetailInfoNewActivity;
import com.user.activity.LoginActivity;

/**
 * 未领取任务适配器
 *
 * @author llp
 */
public class GetNoGoingAdapter extends BaseAdapter {
    private Context context;
    private List<NoGoingTaskInfo> noGoingTaskInfos;

    public GetNoGoingAdapter(Context context,
                             List<NoGoingTaskInfo> noGoingTaskInfos) {
        this.context = context;
        this.noGoingTaskInfos = noGoingTaskInfos;
    }

    @Override
    public int getCount() {
        return noGoingTaskInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return noGoingTaskInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task_nogoing, parent,false);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        final NoGoingTaskInfo noGoingTaskInfo = noGoingTaskInfos.get(position);
        if (Util.IsNotNUll(noGoingTaskInfo.logo) && Utils.checkUrl(noGoingTaskInfo.logo)) {
            ImageLoadManager.getLoaderInstace().disPlayNormalImg(
                    noGoingTaskInfo.logo, viewholder.mIV_0,
                    R.drawable.pusher_logo);
        } else {
            viewholder.mIV_0.setImageResource(R.drawable.pusher_logo);
        }
        viewholder.mTV_1.setText(noGoingTaskInfo.getAmount());
        viewholder.mTV_2.setText(noGoingTaskInfo.taskName);
        viewholder.mTV_3.setText(noGoingTaskInfo.taskGeneralInfo);
//        if(noGoingTaskInfo.taskType==1){
////签约
//            viewholder.mIV_4.setImageResource(R.drawable.team_qianyue);
//
//        }else if(noGoingTaskInfo.taskType==2){
//            //分享
//            viewholder.mIV_4.setImageResource(R.drawable.team_share);
//        }else if(noGoingTaskInfo.taskType==3){
//            // 下载
//            viewholder.mIV_4.setImageResource(R.drawable.team_down);
//        }else{
//            viewholder.mIV_4.setImageResource(R.drawable.team_qianyue);
//        }

        viewholder.mIV_4.setText(noGoingTaskInfo.tagName);
        if(!TextUtils.isEmpty(noGoingTaskInfo.tagColorCode)){
            viewholder.mIV_4.setBackgroundColor( Color.parseColor(noGoingTaskInfo.tagColorCode));
        }
//        SpannableStringBuilder style = null;
//        switch (noGoingTaskInfo.taskType){
//            case 1:
//                //签约//
 //               style =  UIHelper.setStyleColorByColor(context, noGoingTaskInfo.taskTypeName.toString(), noGoingTaskInfo.taskGeneralInfo.toString(), R.color.white, R.color.tv_bg_color_1);
//                break;
//            case 2:
//                //分享
//                style =  UIHelper.setStyleColorByColor(context,noGoingTaskInfo.taskTypeName.toString(),noGoingTaskInfo.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_3);
//                break;
//            case 3:
//                //下载
//                style =  UIHelper.setStyleColorByColor(context,noGoingTaskInfo.taskTypeName.toString(),noGoingTaskInfo.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_2);
//                break;
//            default:
//                style =  UIHelper.setStyleColorByColor(context,noGoingTaskInfo.taskTypeName.toString(),noGoingTaskInfo.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_1);
//                break;
//        }
//        viewholder.tv_pusher_taskType_content.setText(style);

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context,
                            TaskDetailInfoNewActivity.class);
                    intent.putExtra("TaskId", noGoingTaskInfo.taskId);
                    intent.putExtra("TaskName", noGoingTaskInfo.taskName);
                    context.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder{
        private ImageView mIV_0;//图标
        private TextView mTV_1;//金额
        private TextView mTV_2;//title
        private TextView mTV_3;//content
        private TextView mIV_4;//icon flag

        public ViewHolder(View view) {
            mIV_0 = (ImageView)view.findViewById(R.id.iv_team_0);
            mTV_1 = (TextView)view.findViewById(R.id.tv_team_1);
            mTV_2 = (TextView)view.findViewById(R.id.tv_team_2);
            mTV_3 = (TextView)view.findViewById(R.id.tv_team_3);
            mIV_4 = (TextView)view.findViewById(R.id.iv_team_4);
        }
    }
}
