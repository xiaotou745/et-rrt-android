package com.renrentui.db;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 任务模板信息表字段
 */
public class TaskTempleColumn implements BaseColumns {
    public TaskTempleColumn() {
    }
    public static final String USER_ID = "USER_ID";//用户id
    public static final String TASK_ID = "TASK_ID";//任务id
    public static final String TEAM_TYPE = "TEAM_TYPE";//组的类型 (1 文字组 2 图片组 3多图组)
    public static final String TEAM_NUM = "TEAM_NUM";// 组号
    public static final String TEAM_NUM_INDEX = "TEAM_NUM_INDEX";//组中的顺序号
    public static final String TEAM_CONTENT_TYPE = "TEAM_CONTENT_TYPE";//组控件类型 1是Text 文本框 2是FileUpload 图片上传
    public static final String TEAM_CONTENT_KEY = "TEAM_CONTENT_KEY";//组控件内容的key
    public static final String TEAM_CONTENT_VALUE = "TEAM_CONTENT_VALUE";//组控件内容的value
    public static final String[] TASK_TEMPLE_PROJECT = {_ID,USER_ID,TASK_ID,TEAM_TYPE,TEAM_NUM,TEAM_NUM_INDEX,TEAM_CONTENT_TYPE,TEAM_CONTENT_KEY,TEAM_CONTENT_VALUE};




}
