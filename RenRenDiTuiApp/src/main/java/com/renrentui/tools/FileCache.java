package com.renrentui.tools;

import java.io.File;

import android.content.Context;
/**
 * 文件缓存类
 * @author back
 *
 */
public class FileCache {
	
	private String dirString;
	public FileCache(Context context) {
		dirString = getCacheDir();
		boolean res = FileUtils.createDirectory(dirString);
	}
	
	/**
	 * 存放图片的路径
	 */
	public static String getCacheDir() {
		return FileUtils.getSaveFilePath() + "/img/";
	}
	/**
	 * 存放图片的路径
	 */
	public static String getFileDir(String path) {
		String name = String.valueOf(path.hashCode());
		return getCacheDir() + name;
	}

	/**
	 * 图片所在的路径  存与取
	 */
	public File getImgFile(String path) {
		String name = String.valueOf(path.hashCode());
//		File file = new File(getCacheDir() + name + ".jpg");
		File file = new File(getCacheDir() + name);
		return file;
	}
	/**
	 * 清除所在路径的内容
	 */
	public void clear(){
		FileUtils.deleteDirectoty(FileUtils.getSaveFilePath());
		FileUtils.createDirectory(getCacheDir());
	}
	
}
