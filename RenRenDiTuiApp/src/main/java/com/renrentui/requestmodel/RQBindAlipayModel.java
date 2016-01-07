package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2016/1/6 0006.
 * 绑定提现账户
 */
public class RQBindAlipayModel extends RQBase {
    private String userId;//地推员id
    private String phoneNo;//手机号
    private String aliAccount;//支付宝账号
    private String aliName;//支付宝姓名
    private String verifyCode;//验证码

    public RQBindAlipayModel() {
        super();
    }

    public RQBindAlipayModel(String userId, String phoneNo, String aliAccount, String aliName, String verifyCode) {
        super();
        this.userId = userId;
        this.phoneNo = phoneNo;
        this.aliAccount = aliAccount;
        this.aliName = aliName;
        this.verifyCode = verifyCode;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAliAccount() {
        return aliAccount;
    }

    public String getAliName() {
        return aliName;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    @Override
    public String toString() {
        return "RQBindAlipayModel{" +
                "userId='" + userId + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", aliAccount='" + aliAccount + '\'' +
                ", aliName='" + aliName + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
