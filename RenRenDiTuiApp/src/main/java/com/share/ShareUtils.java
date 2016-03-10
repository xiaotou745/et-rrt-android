package com.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.renrentui.util.ToastUtil;
import com.share.bean.ShareBean;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
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
    private Class mObj;

    /**
     * 初始化友盟分享设置
     *
     */
    public static void initUMShareControl(){

        //微信 appid appsecret
        PlatformConfig.setWeixin("wx372230899f9b558e", "06b4552fd896c6616d41d2c2d1cd168f");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("2925603791", "dbbda94f1cc82d52ec10c597209af946");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1105086358", "AWh9dDtypExUAPXm");
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
        //Log.LOG= false;//不显示日志
        //Config.IsToastTip = false;//不显示友盟 的 toast

    }

    public ShareUtils(Context context,Activity act,ShareBean bean){
        this.context = context;
        this.act = act;
        mShareBean = bean;
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
                mShareAction.withMedia(new UMImage(context, "http://m.renrentui.me/img/144_qs.png"));
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
                if(mShareAPI.isInstall(act,SHARE_MEDIA.WEIXIN_CIRCLE)) {
                    mShareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                }else{
                    ToastUtil.show(context,"请安装微信客户端");
                    return;
                }
            }else if(share_media.equals(SHARE_MEDIA.WEIXIN)){
                //微信
                if(mShareAPI.isInstall(act,SHARE_MEDIA.WEIXIN)) {
                    mShareAction.setPlatform(SHARE_MEDIA.WEIXIN);
                }else{
                    ToastUtil.show(context,"请安装微信客户端");
                    return;
                }
            }else if(share_media.equals(SHARE_MEDIA.QQ)){
                //qq
                if(mShareAPI.isInstall(act,SHARE_MEDIA.QQ)) {
                    mShareAction.setPlatform(SHARE_MEDIA.QQ);
                }else{
                    ToastUtil.show(context,"请安装QQ客户端");
                    return;
                }
            } else if(share_media.equals(SHARE_MEDIA.QZONE)){
                //qq 控件
                if(mShareAPI.isInstall(act,SHARE_MEDIA.QZONE)) {
                    mShareAction.setPlatform(SHARE_MEDIA.QZONE);
                }else{
                    ToastUtil.show(context,"请安装QQ客户端");
                    return;
                }
            }else  if(share_media.equals(SHARE_MEDIA.SINA)){
                //新浪微博
                    mShareAction.setPlatform(SHARE_MEDIA.SINA);
            }
            if(mShareBean!=null) {
                mShareAction.withTitle(mShareBean.getStrTitle());
                mShareAction.withFollow(mShareBean.getStrFollow());
                mShareAction.withTargetUrl(mShareBean.getStrTargetUrl());
                mShareAction.withText(mShareBean.getStrText());
                mShareAction.withMedia(new UMImage(context, "http://m.renrentui.me/img/144_qs.png"));
            }
            mShareAction.setListenerList(umShareListener);
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
    //个人中心招募合伙人

    /**
     * 招募合伙人分享页
     * @param arrs
     */
    public  void  showFindFriendsShareBoard(SHARE_MEDIA[] arrs,Class obj ){
        if(arrs==null || arrs.length<=0 ){
            return;
        }
        mObj = obj;
        ShareAction mShareAction = new ShareAction(act).setDisplayList(arrs);
        mShareAction.setShareboardclickCallback(findFriendsShareBoardlistener);
        mShareAction.open();

    }

    /**
     * 招募合伙人
     */
    private ShareBoardlistener findFriendsShareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform,SHARE_MEDIA share_media) {
            ShareAction mShareAction =new ShareAction(act);
            Intent mIntent = new Intent();
            mIntent.setClass(context,mObj);
            if(share_media.equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
                //微信朋友圈
                if(mShareAPI.isInstall(act,SHARE_MEDIA.WEIXIN_CIRCLE)) {
                    mIntent.putExtra("SHARE_TYPE","WEIXIN_CIRCLE");
                }else{
                    ToastUtil.show(context,"请安装微信客户端");
                    return;
                }
            }else if(share_media.equals(SHARE_MEDIA.WEIXIN)){
                //微信
                if(mShareAPI.isInstall(act,SHARE_MEDIA.WEIXIN)) {
                    mIntent.putExtra("SHARE_TYPE", "WEIXIN");
                }else{
                    ToastUtil.show(context,"请安装微信客户端");
                    return;
                }
            }else if(share_media.equals(SHARE_MEDIA.QQ)){
                //qq
                if(mShareAPI.isInstall(act,SHARE_MEDIA.QQ)) {
                    mIntent.putExtra("SHARE_TYPE", "QQ");
                }else{
                    ToastUtil.show(context,"请安装QQ客户端");
                    return;
                }
            } else if(share_media.equals(SHARE_MEDIA.QZONE)){
                //qq 控件
                if(mShareAPI.isInstall(act,SHARE_MEDIA.QZONE)) {
                    mIntent.putExtra("SHARE_TYPE", "QZONE");
                }else{
                    ToastUtil.show(context,"请安装QQ客户端");
                    return;
                }
            }else  if(share_media.equals(SHARE_MEDIA.SINA)){
                //新浪微博
                mIntent.putExtra("SHARE_TYPE","SINA");
            }else {
                return;
            }
            if(mShareBean!=null) {
               mIntent.putExtra("SHARE_CONTENT",mShareBean);
            }
            context.startActivity(mIntent);
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
