package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2016/1/8 0008.
 * 读取或删除消息
 */
public class RSReadMyMessage extends RSBase {
    public String data;
public RSReadMyMessage(){
    super();
}

    public RSReadMyMessage(String Code, String Msg, String data) {
        super(Code, Msg);
        this.data = data;
    }
}
