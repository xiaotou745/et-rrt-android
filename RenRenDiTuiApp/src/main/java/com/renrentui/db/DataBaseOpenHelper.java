package com.renrentui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 数据库管理类
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = DataBaseOpenHelper.class.getName();

    public static final String DATA_NAME = "renrenditui.db";
    public static final int DATA_VERSION = 1;
    public static final String TASK_TEMPLE_LIB = "task_temple_lib";// 模板信息表
    public static final String CITY_TABLE_LIB="city_table_lib";//城市信息表

    public DataBaseOpenHelper(Context context) {
        super(context, DATA_NAME, null, DATA_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
         StringBuffer strCityTable = new StringBuffer();
        strCityTable.append("CREATE TABLE IF NOT EXISTS ").append(CITY_TABLE_LIB)
                 .append(" ( ")
                  .append(CityColumn._ID).append(" integer primary key autoincrement, ")
                 .append(CityColumn.CITY_FIRST_LETTER).append(" varchar(10) ,")
                .append(CityColumn.CITY_CODE).append(" varchar(30) ,")
                .append(CityColumn.CITY_NAME).append(" varchar(30) ,")
                .append(CityColumn.CITY_TYPE).append(" varchar(40) ,")
                .append(CityColumn.VERSION).append(" varchar(0) ")
         .append(" )");
        db.execSQL(strCityTable.toString());
//        任务模板
        StringBuffer strTaskTemple = new StringBuffer();
        strTaskTemple.append("CREATE TABLE IF NOT EXISTS ").append(TASK_TEMPLE_LIB)
                .append(" ( ")
                .append(TaskTempleColumn._ID).append(" integer primary key autoincrement, ")
                .append(TaskTempleColumn.USER_ID).append(" varchar(30) ,")
                .append(TaskTempleColumn.TASK_ID).append(" varchar(30) ,")
                .append(TaskTempleColumn.TEAM_TYPE).append(" varchar(20) ,")
                .append(TaskTempleColumn.TEAM_NUM).append(" varchar(20) ,")
                .append(TaskTempleColumn.TEAM_NUM_INDEX).append(" varchar(20) ,")
                .append(TaskTempleColumn.TEAM_CONTENT_TYPE).append(" varchar(20) ,")
                .append(TaskTempleColumn.TEAM_CONTENT_KEY).append(" varchar(20) ,")
                .append(TaskTempleColumn.TEAM_CONTENT_VALUE).append(" varchar(200) ")
                .append(" )");
        db.execSQL(strTaskTemple.toString());
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TEMPLE_LIB);
        db.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE_LIB);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TEMPLE_LIB);
        db.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE_LIB);
        onCreate(db);
    }

}