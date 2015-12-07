/**
 * 
 */
package com.task.upload.db;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * 
 * @author jzy
 * @version 2012-5-24 下午5:39:19
 */

public class HandlerDB extends DBConstants {
    protected SQLiteDatabase db;
    protected AtomicInteger mOpenCounter = new AtomicInteger();

    public HandlerDB() {
    }

    public synchronized SQLiteDatabase getDBInstance(Context cx) {
        if (db == null || !db.isOpen()) {
            db = new DatabaseHelper(cx).getWritableDatabase();
        }
        return db;
    }

    public class DatabaseHelper extends SQLiteOpenHelper {

        private Context cx;

        public DatabaseHelper(Context context) {
            // TODO Auto-generated constructor stub
            super(context, DB_NAME, null, DBVERSION);
            this.cx = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            // 预编译语句
            SQLiteStatement ssmt = db
                    .compileStatement(CREATE_CACHE_TABLE_SQL);
            ssmt.execute();
            ssmt.close();

            SQLiteDatabase.releaseMemory();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            SQLiteStatement ssmt = db
                    .compileStatement(CREATE_CACHE_TABLE_SQL);
            ssmt.execute();
            ssmt.close();
            SQLiteDatabase.releaseMemory();
        }
    }

}
