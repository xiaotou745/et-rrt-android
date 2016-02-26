package com.kf;

import android.content.Context;

import com.sobot.chat.SobotApi;
import com.sobot.chat.api.model.Information;

/**
 * Created by Administrator on 2016/2/26 0026.
 * 客服工具类
 */
public class SobotUtils {
    private static final String STR_SOBOT_APPKEY = "5bc5d60ca874445992e8d7619606921a";//智齿客服appkey

    /**
     * 启动客服
     * @param context  activity 对象
     */
    public static void showSobotDialog(Context context){
        Information info = new Information();
        info.setAppKey(STR_SOBOT_APPKEY);
         // 选填，默认为"#09aeb0". 可以设置头部背景，提交评价背景，相似问题字体颜色 和富文本类型中“阅读全文”字体颜色
        info.setColor("");
        info.setUid("");//选填，设置用户唯一标示
        info.setNickName("");//选填,用户昵称，选填
        info.setPhone("");//选填，用户电话
        info.setEmail("");//选填，邮箱
        /**
         * 智能转人工按钮，默认为false ,只能客服
         * true 人工
         */
        info.setArtificialIntelligence(false);
        SobotApi.startSobotChat(context,info);

    }

}
