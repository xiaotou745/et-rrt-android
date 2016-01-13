package com.renrentui.resultmodel;

import com.renrentui.util.TimeUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/1/11 0011.
 *
 * 我的资金明细
 */
public class RSMyCaptailModel extends RSBase {
private Result data;

    public  RSMyCaptailModel(){
        super();
    }

    public RSMyCaptailModel(String Code, String Msg, Result data) {
        super(Code, Msg);
        this.data = data;
    }

    public Result getData() {
        return data;
    }

    public class Result implements Serializable{
        private int count;//总数
        private int nextId;//下一页记录开始ID
        private List<DataList> content;//

        public int getCount() {
            return count;
        }

        public int getNextId() {
            return nextId;
        }

        public List<DataList> getContent() {
            return content;
        }

        public Result() {

        }

        public Result(int count, int nextId, List<DataList> content) {
            this.count = count;
            this.nextId = nextId;
            this.content = content;
        }
    }
    public class DataList  implements Serializable {
       private String  id ;//记录id
        private double   amount ;//金额
        private String recordType; //;账单类型编码（int类型）
        private String recordTypeName; //账单类型描述文本
        private String operateTime ;//入账时间
        private String remark ;//账单描述

        public DataList() {
        }

        public DataList(String id, double amount, String recordType, String recordTypeName, String operateTime, String remark) {
            this.id = id;
            this.amount = amount;
            this.recordType = recordType;
            this.recordTypeName = recordTypeName;
            this.operateTime = operateTime;
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public String getAmount() {
            DecimalFormat df = new DecimalFormat("0.00");
            String db = df.format(amount);
            return db;
        }

        public String getRecordType() {
            return recordType;
        }

        public String getRecordTypeName() {
            return recordTypeName;
        }

        public String getOperateTime() {
          return   TimeUtils.StringPattern(operateTime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm");
        }

        public String getRemark() {
            return remark;
        }
    }
}
