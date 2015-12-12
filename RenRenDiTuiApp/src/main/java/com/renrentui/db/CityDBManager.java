package com.renrentui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.renrentui.db.Bean.CityDBBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/10 0010.
 */
public class CityDBManager {
    private DataBaseOpenHelper dbHelper = null;
    public SQLiteDatabase CityDB = null;
    private Context context;
    private String tableName = DataBaseOpenHelper.CITY_TABLE_LIB;

    public CityDBManager(Context con) {
        context = con;
    }

    private void open() {

        if (dbHelper == null) {
            dbHelper = new DataBaseOpenHelper(context);
        }
        if (CityDB == null) {
            CityDB = dbHelper.getWritableDatabase();
        }
    }

    private void closed() {
        if (CityDB != null) {
            CityDB.close();
            CityDB = null;
        }
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }
    //    批量添加
    public void addCityOnce(CityDBBean data ,String version){
        try{
            if (data != null) {
                    open();
                    ContentValues values = new ContentValues();
                    values.put(CityColumn.CITY_FIRST_LETTER, data.CITY_FIRST_LETTER.trim());
                    values.put(CityColumn.CITY_CODE, data.CITY_CODE);
                    values.put(CityColumn.CITY_NAME, data.CITY_NAME);
                    values.put(CityColumn.CITY_TYPE, data.CITY_TYPE);
                    values.put(CityColumn.VERSION, version);
                    CityDB.insert(tableName, null, values);

            }
        }catch(Exception e) {

        }finally {
            closed();
        }
    }
//    批量添加
    public void addCityList(ArrayList<CityDBBean> data ,String version){
        try{
        if (data != null && data.size() > 0) {
            int isize = data.size();
            open();
            for (int i = 0; i < isize; i++) {
                CityDBBean bean = data.get(i);
                ContentValues values = new ContentValues();
                values.put(CityColumn.CITY_FIRST_LETTER, bean.CITY_FIRST_LETTER.trim());
                values.put(CityColumn.CITY_CODE, bean.CITY_CODE);
                values.put(CityColumn.CITY_NAME, bean.CITY_NAME);
                values.put(CityColumn.CITY_TYPE, bean.CITY_TYPE);
                values.put(CityColumn.VERSION, version);
                CityDB.insert(tableName, null, values);
            }
        }
        }catch(Exception e) {

        }finally {
            closed();
        }
    }

    //    批量添加
    public void delCityList(){
        try{
            String sql = "DELETE FROM " + tableName;
            CityDB.execSQL(sql);
            CityDB.execSQL("update sqlite_sequence set seq=0 where name='"+tableName+"';");
            SQLiteDatabase.releaseMemory();

        }catch(Exception e) {

        }finally {
            closed();
        }
    }
//    获取城市的版本信息
    public String getCiytVersion(){
        String version = "0";
        Cursor cursor = null;
        try {
            open();
            cursor = CityDB.query(true, tableName, CityColumn.CITY_PROJECTION, null, null,
                    null, null, null, "1");
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                version = cursor.getString(5);
            }
        } catch (Exception e) {

        } finally {
            if(cursor!=null){
                cursor.close();
            }
            closed();
        }
        return version;
    }

    /**
     * 获取城市洗洗
     * @param city_type (0:定位城市  1：最近  2：热门城市  3：列表  4：历史)
     * @return
     */
    public ArrayList<CityDBBean> getAllHotCity(String city_type){
        ArrayList<CityDBBean>  ResultData = new ArrayList<CityDBBean>();
        Cursor cursor = null;
        try {
            open();
            String str_where = CityColumn.CITY_TYPE +" = ? ";
            String[] str_args= {city_type};
            cursor = CityDB.query(tableName, CityColumn.CITY_PROJECTION, str_where,
                    str_args, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CityDBBean bean = new CityDBBean();
                    bean.setCITY_FIRST_LETTER(cursor.getString(1));
                    bean.setCITY_CODE(cursor.getString(2));
                    bean.setCITY_NAME(cursor.getString(3));
                    bean.setCITY_TYPE(cursor.getString(4));
                    bean.setVERSION(cursor.getString(5));
                    ResultData.add(bean);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            ResultData.clear();
        } finally {
            if(cursor!=null){
                cursor.close();
            }
            closed();
        }
        return ResultData;
    }
//    获取全部城市信息
public ArrayList<CityDBBean> getAllCity(){
    ArrayList<CityDBBean>  ResultData = new ArrayList<CityDBBean>();
    Cursor cursor = null;
    try {
        open();
            cursor = CityDB.query(tableName, CityColumn.CITY_PROJECTION, null,
                null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CityDBBean bean = new CityDBBean();
                    bean.setCITY_FIRST_LETTER(cursor.getString(1));
                    bean.setCITY_CODE(cursor.getString(2));
                    bean.setCITY_NAME(cursor.getString(3));
                    bean.setCITY_TYPE(cursor.getString(4));
                    bean.setVERSION(cursor.getString(5));
                    ResultData.add(bean);
                } while (cursor.moveToNext());
            }
    } catch (Exception e) {
        ResultData.clear();
    } finally {
        if(cursor!=null){
            cursor.close();
        }
        closed();
    }
    return ResultData;
}
    public ArrayList<CityDBBean> getKeyCityByName( String strkey){
        ArrayList<CityDBBean>  ResultData = new ArrayList<CityDBBean>();
        open();
        Cursor cursor = null;
        try {
             cursor = CityDB.rawQuery("select * from city_table_lib where CITY_NAME like \"%" + strkey + "%\" or CITY_CODE like \"%"
                    + strkey + "%\"", null);
            CityDBBean bean = null;
            while (cursor.moveToNext()) {
                bean = new CityDBBean();
                bean.setCITY_FIRST_LETTER(cursor.getString(1));
                bean.setCITY_CODE(cursor.getString(2));
                bean.setCITY_NAME(cursor.getString(3));
                bean.setCITY_TYPE(cursor.getString(4));
                bean.setVERSION(cursor.getString(5));
                ResultData.add(bean);
            }
        } catch (Exception e) {
        }finally {
            cursor.close();
            closed();
        }


        return ResultData;
    }
//    获取城市的code 通过名字
    public String getCityCodeByName(String name){
        String str_code = "";
        Cursor cursor=null;
        try {
            if (TextUtils.isEmpty(name)) {
                str_code= "0";
            } else {
                open();
                String selection = CityColumn.CITY_NAME + " = ? ";
                String[] selectionArgs = { name };
                cursor = CityDB.query(tableName, CityColumn.CITY_PROJECTION, selection,
                        selectionArgs, null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    str_code = cursor.getString(2);
                }
            }
        }catch (Exception e){

        }finally {
            if(cursor!=null){
                cursor.close();
            }
            closed();
        }
            return str_code;
    }

}
