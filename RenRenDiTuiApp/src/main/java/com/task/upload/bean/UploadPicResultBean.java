package com.task.upload.bean;

/**
 * Created by Administrator on 2015/12/7 0007.
 * 图片上传结果类
 */
public class UploadPicResultBean {
   private int Code ;//状态 1 成功,其它失败
    private String Message;// Message 提示信息
    private  ResultBean  Result;// Result 返回的文件信息

    public int getCode() {
        return Code;
    }

    public String getMessage() {
        return Message;
    }

    public ResultBean getResult() {
        return Result;
    }

    public void setCode(int code) {
        Code = code;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setResult(ResultBean result) {
        Result = result;
    }

    public class ResultBean{
      private int   Code  ;//状态 1 成功,其它失败
      private String  Message ;// 提示信息
      private DataBean  Data  ;//返回的文件信息

        public void setCode(int code) {
            Code = code;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public void setData(DataBean data) {
            Data = data;
        }

        public int getCode() {
            return Code;
        }

        public String getMessage() {
            return Message;
        }

        public DataBean getData() {
            return Data;
        }
    }
    public class DataBean{
        private String FileUrl  ;//文件的访问地址(http://192.168.1.38/edsuploadapi/Business/2015/10/13/13/322547f79e.jpg)
        private String RelativePath;// 保存到数据库的相对路径(edsuploadapi/Business/2015/10/13/13/322547f79e.jpg)
        private String OriginalName ;//上传的文件名称 abc.jpg
        private String ModifyOriginalName ;//上传的文件名称修改后的 322547f79e_0_0.jpg

        public void setFileUrl(String fileUrl) {
            FileUrl = fileUrl;
        }

        public void setRelativePath(String relativePath) {
            RelativePath = relativePath;
        }

        public void setOriginalName(String originalName) {
            OriginalName = originalName;
        }

        public void setModifyOriginalName(String modifyOriginalName) {
            ModifyOriginalName = modifyOriginalName;
        }

        public String getFileUrl() {
            return FileUrl;
        }

        public String getRelativePath() {
            return RelativePath;
        }

        public String getOriginalName() {
            return OriginalName;
        }

        public String getModifyOriginalName() {
            return ModifyOriginalName;
        }
    }


}
