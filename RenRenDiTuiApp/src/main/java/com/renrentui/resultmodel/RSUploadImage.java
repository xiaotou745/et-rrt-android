package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2016/1/14 0014.
 *
 * 上传图片
 */
public class RSUploadImage extends RSBase {
    public ImageInfo data;

    public RSUploadImage(){
        super();
    }

    public RSUploadImage(String Code, String Msg, ImageInfo data) {
        super(Code, Msg);
        this.data = data;
    }

    public void setData(ImageInfo data) {
        this.data = data;
    }

    public ImageInfo getData() {
        return data;
    }
}
