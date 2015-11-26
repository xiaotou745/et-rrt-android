package com.renrentui.requestmodel;

import com.renrentui.interfaces.IRqHandlerMsg;
import android.os.Handler;
import android.os.Message;
/**
 * 自定义的处理api返回的handler
 * @author EricHu
 * @param <T>
 *
 */
public class RQHandler<T> extends Handler {
    private IRqHandlerMsg<T> iRqHandlerMsg;
    
	public RQHandler(IRqHandlerMsg<T> iRqHandlerMsg) {
		super();
		this.iRqHandlerMsg = iRqHandlerMsg;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		iRqHandlerMsg.onBefore();
		super.handleMessage(msg);
		switch (msg.what) {
		case ResultMsgType.NetworkNotValide:
			iRqHandlerMsg.onNetworknotvalide();
			break;
		case ResultMsgType.Success:
			iRqHandlerMsg.onSuccess((T) msg.obj);
			break;
		case ResultMsgType.ServiceErr:
			iRqHandlerMsg.onSericeErr((T) msg.obj);
			break;
		case ResultMsgType.ServiceExp:
			iRqHandlerMsg.onSericeExp();
			break;

		default:
			break;
		}
	}
	

}
