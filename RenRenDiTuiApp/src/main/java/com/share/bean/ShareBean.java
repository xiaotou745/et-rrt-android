package com.share.bean;

import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/24 0024.
 *  分享数据bean
 */
public class ShareBean implements Serializable{
    private String strTitle;
    private String strText;
    private String strTargetUrl;
    private String strFollow;
    private String umImage;
    private String umVideo;
    private String uMusic;

    public ShareBean() {
    }

    public String getStrTitle() {
        return strTitle;
    }



    public String getStrTargetUrl() {
        return strTargetUrl;
    }

    public String getStrFollow() {
        return strFollow;
    }


    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrText() {
        return strText;
    }

    public void setStrText(String strText) {
        this.strText = strText;
    }

    public void setStrTargetUrl(String strTargetUrl) {
        this.strTargetUrl = strTargetUrl;
    }

    public void setStrFollow(String strFollow) {
        this.strFollow = strFollow;
    }


    public void setuMusic(String mediaUrl,UMImage image,String author,String title,String targetUrl,String thumb) {
        UMusic mm = new UMusic(mediaUrl);
        mm.setAuthor(author);
        mm.setThumb(image);
        mm.setTitle(title);
        mm.setTargetUrl(targetUrl);
        mm.setThumb(thumb);
    }

    public String getUmImage() {
        return umImage;
    }

    public String getUmVideo() {
        return umVideo;
    }

    public String getuMusic() {
        return uMusic;
    }

    public void setUmImage(String umImage) {
        this.umImage = umImage;
    }

    public void setUmVideo(String umVideo) {
        this.umVideo = umVideo;
    }

    public void setuMusic(String uMusic) {
        this.uMusic = uMusic;
    }
}
