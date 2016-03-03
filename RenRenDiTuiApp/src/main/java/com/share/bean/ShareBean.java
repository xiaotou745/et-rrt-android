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
    private UMImage umImage;
    private UMVideo umVideo;
    private UMusic uMusic;

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

    public UMImage getUmImage() {
        return umImage;
    }

    public UMVideo getUmVideo() {
        return umVideo;
    }

    public UMusic getuMusic() {
        return uMusic;
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

    public void setUmImage(UMImage umImage) {
        this.umImage = umImage;
    }

    public void setUmVideo(String videourl) {
       UMVideo umv = new UMVideo(videourl);
        this.umVideo = umv;
    }

    public void setuMusic(String mediaUrl,UMImage image,String author,String title,String targetUrl,String thumb) {
        UMusic mm = new UMusic(mediaUrl);
        mm.setAuthor(author);
        mm.setThumb(image);
        mm.setTitle(title);
        mm.setTargetUrl(targetUrl);
        mm.setThumb(thumb);
        this.uMusic = mm;
    }

    public void setUmVideo(UMVideo umVideo) {
        this.umVideo = umVideo;
    }

    public void setuMusic(UMusic uMusic) {
        this.uMusic = uMusic;
    }
}
