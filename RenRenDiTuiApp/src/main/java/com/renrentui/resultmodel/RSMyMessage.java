package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 我的消息bean
 */
public class RSMyMessage extends RSBase {
    public MyMessageDataBean data;
    public RSMyMessage(){
        super();
    }
    public RSMyMessage( String Code, String Msg,MyMessageDataBean data){
        super(Code,Msg);
        this.data =data;
    }

    @Override
    public String toString() {
        return "RSMyMessage{" +
                "data=" + data +
                '}';
    }
}
