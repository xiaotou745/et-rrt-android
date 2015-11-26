package com.renrentui.util;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * 图片异步加载管理类
 * 
 * @author Administrator
 * 
 */
public class ImageLoadManager {

	private static ImageLoadManager mImageLoaderInstance = null;

	private ImageLoader mImageLoader = null;

	private ImageLoadManager() {
		mImageLoader = ImageLoader.getInstance();
	}

	public void clearMemory() {
		if (mImageLoader != null) {
			mImageLoader.clearMemoryCache();
		}
	}

	public static ImageLoadManager getLoaderInstace() {
		if (mImageLoaderInstance == null) {
			mImageLoaderInstance = new ImageLoadManager();
		}
		return mImageLoaderInstance;
	}

	public void downloadPic(String url, Bitmap bitmap) {
		try {
			mImageLoader.getDiskCache().save(url, bitmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public Bitmap getDiskBitmap(String url) {
		if (mImageLoader.getDiskCache() != null
				&& mImageLoader.getDiskCache().get(url) != null) {
			String filePath = mImageLoader.getDiskCache().get(url).getPath();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
			return bitmap;
		} else {
			return null;
		}
	}

	/**
	 * 加载正常图片
	 * 
	 * @param url
	 * @param imageView
	 * @param resId
	 */
	public void disPlayNormalImg(String url, ImageView imageView, int resId) {
		DisplayImageOptions optionsPhoto = new DisplayImageOptions.Builder()
				.showImageOnLoading(resId)
				// 加载中显示图片
				.showImageForEmptyUri(resId)
				// 空地址显示图片
				.showImageOnFail(resId)
				// 加载失败显示图片
				.cacheInMemory(true)
				// 内存缓存
				.cacheOnDisc(true)
				// 磁盘缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true)// ImageLoader是否会考虑EXIF JPEG图像参数(旋转、翻转)
				.displayer(new SimpleBitmapDisplayer()).build();
		mImageLoader.displayImage(url, imageView, optionsPhoto);
	}
	
	/**
	 * 加载圆角图片
	 * 
	 * @param url
	 * @param imageView
	 * @param resId
	 */
	public void disPlayRoundImg(String url, ImageView imageView, int resId) {
		DisplayImageOptions optionsPhoto = new DisplayImageOptions.Builder()
				.showImageOnLoading(resId)
				// 加载中显示图片
				.showImageForEmptyUri(resId)
				// 空地址显示图片
				.showImageOnFail(resId)
				// 加载失败显示图片
				.cacheInMemory(true)
				// 内存缓存
				.cacheOnDisc(true)
				// 磁盘缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true)// ImageLoader是否会考虑EXIF JPEG图像参数(旋转、翻转)
				.displayer(new RoundedBitmapDisplayer(5)).build();
		mImageLoader.displayImage(url, imageView, optionsPhoto);
	}

	// public void disBlurImg(String url, ImageView
	// imageView,ImageLoadingListener listener){
	// DisplayImageOptions optionsPhoto = new DisplayImageOptions.Builder()
	// .cacheInMemory(false)// 内存缓存
	// .cacheOnDisc(true)// 磁盘缓存
	// .considerExifParams(true)// ImageLoader是否会考虑EXIF JPEG图像参数(旋转、翻转)
	// .displayer(new SimpleBitmapDisplayer()).build();
	// mImageLoader.displayImage(url, imageView, listener);
	// }

	public ImageLoader getCoreImageLoader() {
		return mImageLoader;
	}

}
