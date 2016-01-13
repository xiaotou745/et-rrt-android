package com.renrentui.resultmodel;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/1/8 0008.
 */
public class RSMyFriendModel extends RSBase {
    public Result data;//
    public RSMyFriendModel(){
        super();
    }

    public RSMyFriendModel(String Code, String Msg, Result data) {
        super(Code, Msg);
        this.data = data;
    }

    public class Result {
        private double bonusTotal;//累计合伙人分红
        private String partnerNum;//合伙人个数
        private String recommendPhone;//推荐人手机号

        public String getBonusTotal() {
            DecimalFormat df = new DecimalFormat("0.00");
            String db = df.format(bonusTotal);
            return db;
        }

        public String getPartnerNum() {
            return partnerNum;
        }

        public String getRecommendPhone() {
            return recommendPhone;
        }

        public Result() {
        }

        public Result(String recommendPhone, double bonusTotal, String partnerNum) {
            this.recommendPhone = recommendPhone;
            this.bonusTotal = bonusTotal;
            this.partnerNum = partnerNum;
        }
    }
}
