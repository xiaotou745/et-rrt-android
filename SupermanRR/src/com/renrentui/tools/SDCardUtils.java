package com.renrentui.tools;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class SDCardUtils {
    
    public static boolean isExistSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 创建应用目录
     *
     * @return
     */
    public static String createAppDir() {
        File file = new File(Environment.getExternalStorageDirectory()
                + "/PhotoShare");
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }
}
