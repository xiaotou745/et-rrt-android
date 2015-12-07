package com.renrentui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.renrentui.db.Bean.TaskTempleDBBean;
import com.renrentui.util.Utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/5 0005.
 * 任务模板管理数据库
 */
public class TaskTempleDBManager {
    private DataBaseOpenHelper dbHelper = null;
    public SQLiteDatabase TaskTempleDB = null;
    private Context context;
    private String tableName = DataBaseOpenHelper.TASK_TEMPLE_LIB;

    public TaskTempleDBManager(Context con) {
        context = con;
    }

    private void open() {

        if (dbHelper == null) {
            dbHelper = new DataBaseOpenHelper(context);
        }
        if (TaskTempleDB == null) {
            TaskTempleDB = dbHelper.getWritableDatabase();
        }
    }

    private void closed() {
        if (TaskTempleDB != null) {
            TaskTempleDB.close();
            TaskTempleDB = null;
        }
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }

    /**
     * 添加任务模板信息
     * @param bean
     * @return
     */
    public synchronized long addTaskTempleInfo(TaskTempleDBBean bean){
        long add_id = -1;
        try {
            if (bean != null) {
                open();
                ContentValues values = new ContentValues();
                values.put(TaskTempleColumn.USER_ID, bean.getUSER_ID().trim());
                values.put(TaskTempleColumn.TEAM_NUM, bean.getTEAM_NUM().trim());
                values.put(TaskTempleColumn.TEAM_TYPE, bean.getTEAM_TYPE().trim());
                values.put(TaskTempleColumn.TEAM_NUM_INDEX, bean.getTEAM_NUM_INDEX().trim());
                values.put(TaskTempleColumn.TEAM_CONTENT_TYPE, bean.getTEAM_CONTENT_TYPE().trim());
                values.put(TaskTempleColumn.TEAM_CONTENT_KEY, bean.getTEAM_CONTENT_KEY().trim());
                values.put(TaskTempleColumn.TEAM_CONTENT_VALUE, bean.getTEAM_CONTENT_KEY().trim());
                add_id = TaskTempleDB.insert(tableName, null, values);
            }
        }catch (Exception e){

        }finally {
            closed();
        }
        return add_id;
    }

    /**
     * 单条删除
     * @param bean
     * @return
     */
    public synchronized int delTaskTempleInfo_one(TaskTempleDBBean bean){
        int  del_lines = -1;
        try {
            if (bean != null) {
                open();
                String whereCase=TaskTempleColumn.USER_ID+ " = ?, "+
                       TaskTempleColumn.TEAM_TYPE+" = ?,"+ TaskTempleColumn.TEAM_NUM+" = ?"+
                        TaskTempleColumn.TEAM_NUM_INDEX+" = ?";
                String[] whereArgs = {bean.getUSER_ID(),bean.getTEAM_TYPE(),bean.getTEAM_NUM(),bean.getTEAM_NUM_INDEX()};
                del_lines =  TaskTempleDB.delete(tableName, whereCase, whereArgs);
            }
        }catch (Exception e){

        }finally {
            closed();
        }
        return del_lines;
    }

    /**
     * 删除指定用户id的全部信息
     * @param strUserId
     * @return
     */
    public synchronized int delTaskTempleInfoByUserId(String strUserId){
        int  del_lines = -1;
        try {
            if ( Utils.IsNotNUll(strUserId) ) {
                open();
                String whereCase=TaskTempleColumn.USER_ID+ " = ?";
                String[] whereArgs = {strUserId};
                del_lines =  TaskTempleDB.delete(tableName, whereCase, whereArgs);
            }
        }catch (Exception e){

        }finally {
            closed();
        }
        return del_lines;
    }


    /**
     * 更新单条数据
     * @param bean
     * @return
     */
    public synchronized int updateTempleInfo_one(TaskTempleDBBean bean){
        int up_lines =-1;
        try {
            if (bean != null) {
                open();
                String whereCase = TaskTempleColumn.USER_ID + " = ?, " +
                        TaskTempleColumn.TEAM_TYPE + " = ?," + TaskTempleColumn.TEAM_NUM + " = ?" +
                        TaskTempleColumn.TEAM_NUM_INDEX + " = ?";
                String[] whereArgs = {bean.getUSER_ID(), bean.getTEAM_TYPE(), bean.getTEAM_NUM(), bean.getTEAM_NUM_INDEX()};
                ContentValues values = new ContentValues();
                values.put(TaskTempleColumn.USER_ID, bean.getUSER_ID().trim());
                values.put(TaskTempleColumn.TEAM_NUM, bean.getTEAM_NUM().trim());
                values.put(TaskTempleColumn.TEAM_TYPE, bean.getTEAM_TYPE().trim());
                values.put(TaskTempleColumn.TEAM_NUM_INDEX, bean.getTEAM_NUM_INDEX().trim());
                values.put(TaskTempleColumn.TEAM_CONTENT_TYPE, bean.getTEAM_CONTENT_TYPE().trim());
                values.put(TaskTempleColumn.TEAM_CONTENT_KEY, bean.getTEAM_CONTENT_KEY().trim());
                values.put(TaskTempleColumn.TEAM_CONTENT_VALUE, bean.getTEAM_CONTENT_KEY().trim());
                up_lines = TaskTempleDB.update(tableName, values, whereCase, whereArgs);
            }
        }catch (Exception e){

        }finally {
            closed();
        }
        return up_lines;
    }

    /**
     * 判断指定的信息是否存在
     * @param userId
     * @param TeamType
     * @param TeamNum
     * @param TeamNumIndex
     * @return
     */
    public boolean getTaskTempleByUserIdAndTeamTypeAndTeamNumAndTeamNumIndex(String userId,String TeamType,String TeamNum,String TeamNumIndex) {
        boolean isResult = false;
        try {
            open();
            String str_selection = TaskTempleColumn.USER_ID + " = ? and "+TaskTempleColumn.TEAM_TYPE+" = ? and "+
                    TaskTempleColumn.TEAM_NUM+" = ? and " +TaskTempleColumn.TEAM_NUM_INDEX+" = ? ";
            String[] selectionArgs = {userId,TeamType,TeamNum,TeamNumIndex};
            Cursor mCursor = TaskTempleDB.query(tableName, TaskTempleColumn.TASK_TEMPLE_PROJECT, str_selection, selectionArgs, null, null, null);
            if(mCursor!=null && mCursor.getCount()>0){
                isResult = true;
            }
        } catch (Exception e){

        }finally {
            closed();
        }
        return isResult;
    }

    /**
     * 获取全部的模板信息 根据用户id
     * @return
     */

    public synchronized ArrayList<TaskTempleDBBean> getTaskTempleByUserId(String userId) {
        ArrayList<TaskTempleDBBean> data = new ArrayList<TaskTempleDBBean>();
        try {
            if (!Utils.IsNotNUll(userId)) {
                return data;
            }
            open();
            String str_selection = TaskTempleColumn.USER_ID + " = ?";
            String[] selectionArgs = {userId};
            Cursor mCursor = TaskTempleDB.query(tableName, TaskTempleColumn.TASK_TEMPLE_PROJECT, str_selection, selectionArgs, null, null, null);
            if (mCursor != null && mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                do {
                    TaskTempleDBBean bean = new TaskTempleDBBean();
                    bean.setUSER_ID(mCursor.getString(1));
                    bean.setTEAM_TYPE(mCursor.getString(2));
                    bean.setTEAM_NUM(mCursor.getString(3));
                    bean.setTEAM_NUM_INDEX(mCursor.getString(4));
                    bean.setTEAM_CONTENT_TYPE(mCursor.getString(5));
                    bean.setTEAM_CONTENT_KEY(mCursor.getString(6));
                    bean.setTEAM_CONTENT_VALUE(mCursor.getString(7));
                    data.add(bean);
                } while (mCursor.moveToNext());
            }

        } catch (Exception e){

        }finally {
            closed();
        }
        return data;
    }

    /**
     * 获取全部的模板信息 根据用户id和组号
     * @return
     */
    public synchronized ArrayList<TaskTempleDBBean> getTaskTempleByUserIdAndTeamNum(String userId,String teamNum) {
        ArrayList<TaskTempleDBBean> data = new ArrayList<TaskTempleDBBean>();
        try {
            if (!Utils.IsNotNUll(userId)) {
                return data;
            }
            open();
            String str_selection = TaskTempleColumn.USER_ID + " = ? and "+TaskTempleColumn.TEAM_NUM+" = ? ";
            String[] selectionArgs = {userId,teamNum};
            Cursor mCursor = TaskTempleDB.query(tableName, TaskTempleColumn.TASK_TEMPLE_PROJECT, str_selection, selectionArgs, null, null, null);
            if (mCursor != null && mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                do {
                    TaskTempleDBBean bean = new TaskTempleDBBean();
                    bean.setUSER_ID(mCursor.getString(1));
                    bean.setTEAM_TYPE(mCursor.getString(2));
                    bean.setTEAM_NUM(mCursor.getString(3));
                    bean.setTEAM_NUM_INDEX(mCursor.getString(4));
                    bean.setTEAM_CONTENT_TYPE(mCursor.getString(5));
                    bean.setTEAM_CONTENT_KEY(mCursor.getString(6));
                    bean.setTEAM_CONTENT_VALUE(mCursor.getString(7));
                    data.add(bean);
                } while (mCursor.moveToNext());
            }

        } catch (Exception e){

        }finally {
            closed();
        }
        return data;
    }

    /**
     * 跟新或添加
     * @param bean
     */
    public synchronized void updateOrAddTaskTemplate(TaskTempleDBBean bean){
        try{
            if(bean==null){
                return;
            }
            boolean isAdd = getTaskTempleByUserIdAndTeamTypeAndTeamNumAndTeamNumIndex(bean.getUSER_ID(),bean.getTEAM_TYPE(),bean.getTEAM_NUM(),bean.getTEAM_NUM_INDEX());
            if(isAdd){
                // 更新
                this.updateTempleInfo_one(bean);
            }else{
                //添加
                this.addTaskTempleInfo(bean);
            }
        }catch (Exception e){

        }finally {
            closed();
        }
    }


}
