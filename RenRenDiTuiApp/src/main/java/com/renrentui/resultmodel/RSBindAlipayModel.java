package com.renrentui.resultmodel;

import java.util.Objects;

/**
 * Created by Administrator on 2016/1/6 0006.
 */
public class RSBindAlipayModel extends  RSBase {

    //private Objects result;
    public RSBindAlipayModel() {
        super();
    }
    public RSBindAlipayModel(String code, String msg ,Objects result) {
        super(code,msg);
       // this.result = result;
    }
    public RSBindAlipayModel(String code, String msg) {
        super(code,msg);
        // this.result = result;
    }


}
