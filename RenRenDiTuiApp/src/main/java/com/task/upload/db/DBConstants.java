/**
 * 
 */
package com.task.upload.db;

/**
 * 图片上传时的数据信息
 */

public class DBConstants {
    public DBConstants() {
    }

    // //数据库文件目标存放路径为系统默认位置
    protected static final String DB_NAME = ".db";

    protected static final int DBVERSION = 1; // 数据库版本

    protected static final String CACHE_TABLE_NAME = "download_lib";
    protected static final String CREATE_CACHE_TABLE_SQL = "" + "CREATE TABLE " + CACHE_TABLE_NAME
            + " ("
            + "id INTEGER PRIMARY KEY NOT NULL, " + "user_id TEXT, " + "task_id TEXT, "+" team_tag TEXT , "
            +"team_type TEXT , "+ "team_num TEXT , "+"team_position TEXT , "+"status TEXT , "
            +"path TEXT ,"+"create_time TEXT"
            +  ")";
}
