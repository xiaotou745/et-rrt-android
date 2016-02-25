package com.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.share.bean.ShareBean;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * Created by Administrator on 2016/2/24 0024.
 *
 * 分享功能的工具类
 */
public class ShareUtils {
    private Context  context;
    private Activity act;
    private ShareBean mShareBean;
    private UMShareAPI mShareAPI;

    /**
     * 初始化友盟分享设置
     *
     */
    public static void initUMShareControl(){

        //微信 appid appsecret
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
//
//        //支付宝 appid
//        PlatformConfig.setAlipay("2015111700822536");
//        //易信 appkey
//        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
//        //人人 appid appkey appsecret
//        PlatformConfig.setRenren("201874","28401c0964f04a72a14c812d6132fcef","3bf66e42db1e4fa9829b955cc300b737");
//        //Twitter appid appkey
//        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
//        //Pinterest appid
//        PlatformConfig.setPinterest("1439206");
//        //来往 appid appkey
//        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");

    }

    public ShareUtils(Context context,Activity act){
        this.context = context;
        this.act = act;
        mShareAPI =  UMShareAPI.get(context);
    }


    /**
     * 使用默认的分享提示框
     * @param  arrs  分享平台
     */
    public  void  showDefaultShareBoard(SHARE_MEDIA[] arrs,boolean  isBoarad){
        if(arrs==null || arrs.length<=0 ){
            return;
        }
        ShareAction mShareAction = new ShareAction(act).setDisplayList(arrs);
        if(isBoarad){
            //使用控制板事件
            mShareAction.setShareboardclickCallback(shareBoardlistener);
        }else {
            mShareAction.setListenerList(umShareListener);
            if(mShareBean!=null) {
                mShareAction.withTitle(mShareBean.getStrTitle());
                mShareAction.withFollow(mShareBean.getStrFollow());
                mShareAction.withTargetUrl(mShareBean.getStrTargetUrl());
                mShareAction.withText(mShareBean.getStrText());
                mShareAction.withMedia(mShareBean.getUmImage());
                mShareAction.withMedia(mShareBean.getUmVideo());
                mShareAction.withMedia(mShareBean.getuMusic());
            }
        }
        mShareAction.open();
    }

    /**
     * 控制板监听
     */
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform,SHARE_MEDIA share_media) {
            ShareAction mShareAction =new ShareAction(act);
            if(share_media.equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
                //微信朋友圈
                mShareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
            }else if(share_media.equals(SHARE_MEDIA.WEIXIN)){
                //微信
                mShareAction.setPlatform(SHARE_MEDIA.WEIXIN);
            }else if(share_media.equals(SHARE_MEDIA.QQ)){
                //qq
                mShareAction.setPlatform(SHARE_MEDIA.QQ);
            } else if(share_media.equals(SHARE_MEDIA.QZONE)){
                //qq 控件
                mShareAction.setPlatform(SHARE_MEDIA.QZONE);
            }else  if(share_media.equals(SHARE_MEDIA.SINA)){
                //新浪微博
                mShareAction.setPlatform(SHARE_MEDIA.SINA);
            }
            if(mShareBean!=null) {
                mShareAction.withTitle(mShareBean.getStrTitle());
                mShareAction.withFollow(mShareBean.getStrFollow());
                mShareAction.withTargetUrl(mShareBean.getStrTargetUrl());
                mShareAction.withText(mShareBean.getStrText());
                mShareAction.withMedia(mShareBean.getUmImage());
                mShareAction.withMedia(mShareBean.getUmVideo());
                mShareAction.withMedia(mShareBean.getuMusic());
            }
            mShareAction.setCallback(umShareListener);
            mShareAction.share();
        }
    };
    /**
     * 分享结果监听
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(act, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(act,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(act,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 分享的回调接口
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void UMShareActivityResult(int requestCode, int resultCode, Intent data){
        UMShareAPI.get(act).onActivityResult(requestCode, resultCode, data);
    }

}
