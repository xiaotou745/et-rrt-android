package com.task.service;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.NoGoingTaskInfo;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.Utils;
import com.task.activity.TaskDetailInfoActivity;
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
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_task_nogoing, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        final NoGoingTaskInfo noGoingTaskInfo = noGoingTaskInfos.get(position);
        if (Util.IsNotNUll(noGoingTaskInfo.logo) && Utils.checkUrl(noGoingTaskInfo.logo)) {
            ImageLoadManager.getLoaderInstace().disPlayNormalImg(
                    noGoingTaskInfo.logo, viewholder.icon_pusher,
                    R.drawable.pusher_logo);
        } else {
            viewholder.icon_pusher.setImageResource(R.drawable.pusher_logo);
        }
        viewholder.tv_Pusher_taskName.setText(noGoingTaskInfo.taskName);
//        viewholder.tv_pusher_taskType_content.setText(noGoingTaskInfo.taskTypeName+noGoingTaskInfo.taskGeneralInfo);
        viewholder.tv_pusher_amount.setText(noGoingTaskInfo.getAmount());

        //内容信息变换
        String strType =  " "+noGoingTaskInfo.taskTypeName.toString()+" ";
        String strTypeContent =strType +" "+noGoingTaskInfo.taskGeneralInfo.toString();
        int fstart = strTypeContent.indexOf(noGoingTaskInfo.taskTypeName.toString());
        int fend = fstart + noGoingTaskInfo.taskTypeName.toString().length();
        int bstart = 0;
        int bend = strType.length();
        SpannableStringBuilder style = new SpannableStringBuilder(strTypeContent);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new BackgroundColorSpan(context.getResources().getColor(R.color.tv_bg_color_1)),bstart,bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        viewholder.tv_pusher_taskType_content.setText(style);

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Utils.getUserDTO(context)==null || Utils.getUserDTO(context).data.userId==""||Utils.getUserDTO(context).data.userId=="0") {
                    Intent intent = new Intent(context,
                            LoginActivity.class);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context,
                            TaskDetailInfoActivity.class);
                    intent.putExtra("TaskId", noGoingTaskInfo.taskId);
                    intent.putExtra("TaskName", noGoingTaskInfo.taskName);
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public ImageView icon_pusher;
        public TextView tv_Pusher_taskName;
        public TextView tv_pusher_taskType_content;
        public LinearLayout ll_pusher_amount;
        public TextView tv_pusher_amount;


        public ViewHolder(View view) {
            icon_pusher = (ImageView) view.findViewById(R.id.icon_pusher);
            tv_Pusher_taskName = (TextView) view.findViewById(R.id.tv_pusher_taskName);
            tv_pusher_taskType_content = (TextView) view
                    .findViewById(R.id.tv_pusher_taskType_content);
            ll_pusher_amount = (LinearLayout) view
                    .findViewById(R.id.ll_amount);
            tv_pusher_amount = (TextView) view.findViewById(R.id.tv_Amount);

        }
    }
}
