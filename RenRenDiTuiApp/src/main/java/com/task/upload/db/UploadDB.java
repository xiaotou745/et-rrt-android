package com.task.upload.db;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import com.renrentui.util.Constants;
import com.task.upload.bean.uploadPicBean;

/**
 * 
 * 上传图片数据库管理
 * 
 */
public class UploadDB extends HandlerDB {


    /**
     * 获取下载数据
     * @param cx
     * @param user_id  用户id
     * @param task_id  任务id
     * @param team_type  组的类型
     * @param team_num  组号
     * @param team_position  族中的位置
     * @param status   状态（-1 为开始上传）
     * @return
     */
    public synchronized Vector<uploadPicBean> getDataList(Context cx, String user_id, String task_id, String team_type,
                                                          String team_num,String team_position,  int status) {
        mOpenCounter.incrementAndGet();
        Vector<uploadPicBean> downloadList = new Vector<uploadPicBean>();
        try {
            String sql = "select * from " + CACHE_TABLE_NAME + " where 1=1";
            if (!TextUtils.isEmpty(user_id)) {
                sql += " and user_id='" + user_id + "'";
            }
            if (!TextUtils.isEmpty(task_id)) {
                sql += " and task_id='" + task_id + "'";
            }
            if (!TextUtils.isEmpty(team_type)) {
                sql += " and team_type='" + team_type + "'";
            }
            if (!TextUtils.isEmpty(team_num)) {
                sql += " and team_num='" + team_num + "'";
            }
            if (!TextUtils.isEmpty(team_position)) {
                sql += " and team_position='" + team_position + "'";
            }

            if (status != -1) {
             //非等待上传
                sql += " and status='" + status + "'";
            }

            sql += " order by id desc";
            Cursor c = getDBInstance(cx).rawQuery(sql, null);
            // 处理信息
            if (c != null) {
                while (c.moveToNext()) {
                    uploadPicBean vo = new uploadPicBean();
                    vo.setUser_id(user_id);
                    vo.setTask_id(task_id);
                    vo.setTag(c.getString(c.getColumnIndex("team_tag")));
                    vo.setTeam_type(c.getString(c.getColumnIndex("team_type")));
                    vo.setTeam_num(c.getString(c.getColumnIndex("team_num")));
                    vo.setTeam_position(c.getString(c.getColumnIndex("team_position")));
                    vo.setUploadStatus(c.getInt(c.getColumnIndex("status")));
                    vo.setTag(c.getString(c.getColumnIndex("path")));
                    vo.setTicket_property(Constants.TYPE_LOCAL);
                    downloadList.add(vo);
                }
            }
            c.close();
            SQLiteDatabase.releaseMemory();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return downloadList;
    }

    /**
     * 添加下载数据
     * @param cx
     * @param vo  数据bean
     * @return
     */
    public synchronized boolean addData(Context cx, uploadPicBean vo) {
        mOpenCounter.incrementAndGet();
        boolean result = false;
        try {
            ContentValues cv = new ContentValues();
            cv.put("user_id", vo.getUser_id());
            cv.put("task_id", vo.getTask_id());
            cv.put("team_tag", vo.getTag());
            cv.put("team_type", vo.getTeam_type());
            cv.put("team_num", vo.getTeam_num());
            cv.put("team_position", vo.getTeam_position());
            cv.put("status",vo.getUploadStatus());
            cv.put("path", vo.getPath());
            cv.put("create_time", System.currentTimeMillis());
            getDBInstance(cx).insert(CACHE_TABLE_NAME, null, cv);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    /**
     * 根据参数信息，更新状态
     * @param cx
     * @param user_id
     * @param task_id
     * @param team_type
     * @param team_num
     * @param team_position
     * @param status
     * @return
     */
    public synchronized boolean updateDataStatusById(Context cx, String user_id, String task_id, String team_type,
                                                     String team_num,String team_position, int status) {
        mOpenCounter.incrementAndGet();
        boolean result = false;
        try {
            String sql = "update " + CACHE_TABLE_NAME + " set status=" + status + " WHERE 1=1";

            if (!TextUtils.isEmpty(user_id)) {
                sql += " and user_id ='" + user_id + "'";
            } if (!TextUtils.isEmpty(task_id)) {
                sql += " and task_id ='" + task_id + "'";
            } if (!TextUtils.isEmpty(team_type)) {
                sql += " and team_type ='" + team_type + "'";
            } if (!TextUtils.isEmpty(team_num)) {
                sql += " and team_num ='" + team_num + "'";
            }
            if (!TextUtils.isEmpty(team_position)) {
                sql += " and team_position ='" + team_position + "'";
            }
            getDBInstance(cx).execSQL(sql);
            SQLiteDatabase.releaseMemory();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    /**
     * 删除下载数据
     * @param cx
     * @param user_id
     * @param task_id
     * @param team_type
     * @param team_num
     * @param team_position
     * @param status
     * @return
     */
    public synchronized boolean delData(Context cx, String user_id, String task_id, String team_type,
                                        String team_num,String team_position) {
        mOpenCounter.incrementAndGet();
        boolean result = false;
        try {

            String sql = "DELETE FROM " + CACHE_TABLE_NAME + " WHERE 1=1";
            // if (!TextUtils.isEmpty(path)) {
            // sql += " and path ='" + path + "'";
            // }
            if (!TextUtils.isEmpty(user_id)) {
                sql += " and user_id ='" + user_id + "'";
            } if (!TextUtils.isEmpty(task_id)) {
                sql += " and task_id ='" + task_id + "'";
            } if (!TextUtils.isEmpty(team_type)) {
                sql += " and team_type ='" + team_type + "'";
            } if (!TextUtils.isEmpty(team_num)) {
                sql += " and team_num ='" + team_num + "'";
            }
            if (!TextUtils.isEmpty(team_position)) {
                sql += " and team_position ='" + team_position + "'";
            }
            getDBInstance(cx).execSQL(sql);
            SQLiteDatabase.releaseMemory();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    /**
     * 清除下载数据
     * 
     * @param cx
     * @return
     */
    public synchronized boolean clearData(Context cx) {
        if (mOpenCounter == null) {
            mOpenCounter = new AtomicInteger();
        }
        mOpenCounter.incrementAndGet();
        try {
            String sql = "DELETE FROM " + CACHE_TABLE_NAME;
            getDBInstance(cx).execSQL(sql);
            SQLiteDatabase.releaseMemory();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
        return true;
    }

    public void close() {
        if (mOpenCounter.decrementAndGet() == 0 && db != null && db.isOpen()) {
            db.close();
        }
        db = null;
    }
}
