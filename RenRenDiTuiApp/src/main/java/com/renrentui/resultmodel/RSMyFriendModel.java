package com.renrentui.resultmodel;

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
        private String bonusTotal;
        private String partnerNum;
        private String recommendPhone;

        public String getBonusTotal() {
            return bonusTotal;
        }

        public String getPartnerNum() {
            return partnerNum;
        }

        public String getRecommendPhone() {
            return recommendPhone;
        }

        public Result() {
        }

        public Result(String recommendPhone, String bonusTotal, String partnerNum) {
            this.recommendPhone = recommendPhone;
            this.bonusTotal = bonusTotal;
            this.partnerNum = partnerNum;
        }
    }
}
