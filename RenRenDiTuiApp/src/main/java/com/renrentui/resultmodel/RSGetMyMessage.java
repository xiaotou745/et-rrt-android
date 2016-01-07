package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2016/1/4 0004.
 * 获取未读消息数量
 */
public class RSGetMyMessage extends RSBase {
    private int data;
    public RSGetMyMessage(){
        super();
    }
    public RSGetMyMessage(String Code, String Msg ,int data ){
        super(Code,Msg);
        this.data = data;
    }

    public int getData() {
        return data;
    }

    @Override
    public String toString() {
        return "RSGetMyMessage{" +
                "Code=" + code +
                "Msg=" + msg +
                "data=" + data +
                '}';
    }
}
