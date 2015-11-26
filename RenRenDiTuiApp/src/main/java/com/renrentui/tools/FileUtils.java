package com.renrentui.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Environment;
import android.provider.ContactsContract.Directory;

/**
 * 文件工具包
 * 
 * @author back
 * @version landingtech_v1
 */
public class FileUtils {

	/**
	 * 判断手机是否有SD卡
	 */
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}

	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath()
					+ File.separator + "data" + File.separator; // filePath:
		}
	}

	/**
	 * SD卡路径
	 */
	public static String getSaveFilePath() {
		return getRootFilePath() + "com.renrenditui" + File.separator + "files";
	}

	/**
	 * 创建文件缓存目录
	 */
	public static boolean createDirectory(String filePath) {
		if (null == filePath) {
			return false;
		}
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}

		return file.mkdirs();
	}

	/**
	 * 删除缓存
	 */
	public static boolean deleteDirectoty(String filePath) {
		if (null == filePath) {
			return false;
		}
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return false;
		}
		if (file.isDirectory()) {
			File[] list = file.listFiles();

			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					deleteDirectoty(list[i].getAbsolutePath());
				} else {
					list[i].delete();
				}
			}
		}
		file.delete();
		return true;
	}

	/**
	 * 删除文件
	 */
	public static boolean deleteFile(String filePathName) {
		if (null == filePathName) {
			return false;
		}
		File file = new File(filePathName);
		if (file == null || !file.exists()) {
			return false;
		}
		file.delete();
		return true;
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean isFile(String fileName) {
		if (null == fileName) {
			return false;
		}
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 读取文件内容
	 */
	public static String readFile(String fileName) {
		File file = new File(fileName);
		StringBuilder sb = new StringBuilder();
		String s = "";
		BufferedReader br = null;
		try {
			// 把读取的字符转utf-8
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "UTF-8"));
			while ((s = br.readLine()) != null) {
				sb.append(s + "\n"); // 读取一行换一换
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

}
