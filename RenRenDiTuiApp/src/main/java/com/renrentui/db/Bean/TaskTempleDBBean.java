package com.renrentui.db.Bean;

/**
 * Created by Administrator on 2015/12/5 0005.
 * 任务模板数据库bean
 */
public class TaskTempleDBBean {
    private   String USER_ID = "";//用户id
    private String TASK_ID;//任务id
    private String TAG;//标签
    private  String TEAM_TYPE = "";//组的类型 (1 文字组 2 图片组 3多图组)
    private  String TEAM_NUM = "";// 组号
    private  String TEAM_NUM_INDEX = "";//组中的顺序号
    private  String TEAM_CONTENT_TYPE = "";//组控件类型 1是Text 文本框 2是FileUpload 图片上传
    private String TEAM_CONTENT_KEY = "";//组控件内容的key
    private  String TEAM_CONTENT_VALUE = "";//组控件内容的value


    public TaskTempleDBBean(){

    }

    public String getUSER_ID() {
        if(USER_ID!=null){
            return USER_ID.trim();
        }else {
            return "";
        }
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getTEAM_NUM() {
        if(TEAM_NUM!=null){
            return TEAM_NUM.trim();
        }else {
            return "";
        }
    }

    public void setTEAM_NUM(String TEAM_NUM) {
        this.TEAM_NUM = TEAM_NUM;
    }

    public String getTEAM_TYPE() {
        return TEAM_TYPE;
    }

    public void setTEAM_TYPE(String TEAM_TYPE) {
        this.TEAM_TYPE = TEAM_TYPE;
    }

    public String getTEAM_NUM_INDEX() {
        return TEAM_NUM_INDEX;
    }

    public void setTEAM_NUM_INDEX(String TEAM_NUM_INDEX) {
        this.TEAM_NUM_INDEX = TEAM_NUM_INDEX;
    }

    public String getTEAM_CONTENT_TYPE() {
        return TEAM_CONTENT_TYPE;
    }

    public void setTEAM_CONTENT_TYPE(String TEAM_CONTENT_TYPE) {
        this.TEAM_CONTENT_TYPE = TEAM_CONTENT_TYPE;
    }

    public String getTEAM_CONTENT_KEY() {
        return TEAM_CONTENT_KEY;
    }

    public void setTEAM_CONTENT_KEY(String TEAM_CONTENT_KEY) {
        this.TEAM_CONTENT_KEY = TEAM_CONTENT_KEY;
    }

    public String getTEAM_CONTENT_VALUE() {
        return TEAM_CONTENT_VALUE;
    }

    public void setTEAM_CONTENT_VALUE(String TEAM_CONTENT_VALUE) {
        this.TEAM_CONTENT_VALUE = TEAM_CONTENT_VALUE;
    }

    public String getTASK_ID() {
        return TASK_ID;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTASK_ID(String TASK_ID) {
        this.TASK_ID = TASK_ID;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }
}
