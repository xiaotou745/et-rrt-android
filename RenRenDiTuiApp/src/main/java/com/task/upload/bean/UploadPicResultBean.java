package com.task.upload.bean;

/**
 * Created by Administrator on 2015/12/7 0007.
 * 图片上传结果类
 */
public class UploadPicResultBean {
  // private int Status ;//状态 1 成功,其它失败
    private int code;//状态
    private String msg;//信息
  //  private String Message;// Message 提示信息
    private  DataBean  data;// Result 返回的文件信息

//    public void setStatus(int status) {
//        Status = status;
//    }
//
//    public void setMessage(String message) {
//        Message = message;
//    }

//
//    public int getStatus() {
//        return Status;
//    }
//
//    public String getMessage() {
//        return Message;
//    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public DataBean getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class DataBean{
        private String remark;//返回的异常信息（为空时表示成功）
        private String fileUrl  ;//文件的访问地址(http://192.168.1.38/edsuploadapi/Business/2015/10/13/13/322547f79e.jpg)
        private String relativePath;// 保存到数据库的相对路径(edsuploadapi/Business/2015/10/13/13/322547f79e.jpg)
        private String originalName ;//上传的文件名称 abc.jpg
        private String modifyOriginalName ;//上传的文件名称修改后的 322547f79e_0_0.jpg

        public DataBean(String remark, String fileUrl, String relativePath, String originalName, String modifyOriginalName) {
            super();
            this.remark = remark;
            this.fileUrl = fileUrl;
            this.relativePath = relativePath;
            this.originalName = originalName;
            this.modifyOriginalName = modifyOriginalName;
        }

        public String getRemark() {
            return remark;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public String getRelativePath() {
            return relativePath;
        }

        public String getOriginalName() {
            return originalName;
        }

        public String getModifyOriginalName() {
            return modifyOriginalName;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public void setRelativePath(String relativePath) {
            this.relativePath = relativePath;
        }

        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }

        public void setModifyOriginalName(String modifyOriginalName) {
            this.modifyOriginalName = modifyOriginalName;
        }
    }


}
