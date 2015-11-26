package com.renrentui.view.city;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CityWorker {

    private CityDataHelper cityData;
    private SQLiteDatabase db;

    public CityWorker(Context context) {
        cityData = new CityDataHelper(context);
        cityData.openDatabase();
        db = cityData.getDatabase();
    }

    public List<CityMode> getProvince() {
        List<CityMode> list = new ArrayList<CityMode>();
        //	list.add(new CityMode());
        Cursor cursor = null;
        try {
            String sql = "select * from province";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "UTF-8");
                CityMode proc = new CityMode();
                proc.setName(name);
                proc.setCountryID(code);
                proc.setAllName(name);
                list.add(proc);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(2);
            String name = new String(bytes, "UTF-8");
            CityMode proc = new CityMode();
            proc.setName(name);
            proc.setCountryID(code);
            list.add(proc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        //list.add(new CityMode());
        return list;
    }

    public List<CityMode> getCityByProvince(String pcode) {
        List<CityMode> list = new ArrayList<CityMode>();
        //	list.add(new CityMode());
        Cursor cursor = null;
        try {
            String sql = "select * from city where pcode='" + pcode + "'";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "UTF-8");
                CityMode city = new CityMode();
                city.setName(name);
                city.setCountryID(code);
                city.setAllName(name);
                list.add(city);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(2);
            String name = new String(bytes, "UTF-8");
            CityMode city = new CityMode();
            city.setName(name);
            city.setCountryID(code);
            city.setAllName(name);
            list.add(city);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        //	list.add(new CityMode());
        return list;
    }

    public String getAddressByArea(int areaId) {
        String area = getArea("" + areaId)[0];
        CityMode city = getCityByArea(""+areaId);
        CityMode province = getProvinceByCity(city.getCountryID());

        return province.getName()+"  "+city.getName()+area;
    }


    public List<CityMode> getAreaByCity(String city) {
        List<CityMode> list = new ArrayList<CityMode>();
        //list.add(new CityMode());
        Cursor cursor = null;
        try {
            String sql = "select * from district where pcode='" + city + "'";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "UTF-8");
                CityMode area = new CityMode();
                area.setName(name);
                area.setCountryID(code);
                area.setAllName(name);
                list.add(area);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(2);
            String name = new String(bytes, "UTF-8");
            CityMode area = new CityMode();
            area.setName(name);
            area.setCountryID(code);
            area.setAllName(name);
            list.add(area);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        //list.add(new CityMode());
        return list;
    }


    /**
     * 根据区域code 获取当前的区域
     *
     * @param code
     * @return
     */
    public String[] getArea(String code) {
        List<CityMode> list = new ArrayList<CityMode>();
        String pcode = null;
        String name = null;
        Cursor cursor = null;
        try {
            String sql = "select * from district where code='" + code + "'";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            pcode = cursor.getString(3);
            byte bytes[] = cursor.getBlob(2);
            name = new String(bytes, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return new String[]{name, pcode};
    }

    /**
     * 根据当前城市pcode 获取当前的城市
     *
     * @param code
     * @return
     */
    public String[] getCity(String code) {
        List<CityMode> list = new ArrayList<CityMode>();
        String pcode = null;
        String name = null;
        Cursor cursor = null;
        try {
            String sql = "select * from city where code='" + code + "'";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            pcode = cursor.getString(3);
            byte bytes[] = cursor.getBlob(2);
            name = new String(bytes, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return new String[]{name, pcode};
    }

    /**
     * 根据当前省市pcode 获取当前的省市
     *
     * @param code
     * @return
     */
    public String getProvince(String code) {
        List<CityMode> list = new ArrayList<CityMode>();
        String name = null;
        Cursor cursor = null;
        try {
            String sql = "select * from province where code='" + code + "'";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            byte bytes[] = cursor.getBlob(2);
            name = new String(bytes, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return name;
    }

    public void closeCityWorker() {
        cityData.closeDatabase();
        db.close();
    }

    /**
     * 根据区的Id获取所需城市
     *
     * @param aCode
     * @return
     */
    public CityMode getCityByArea(String aCode) {
        List<CityMode> list = new ArrayList<CityMode>();
        //	list.add(new CityMode());
        Cursor cursor = null;
        try {
            String sql = "select b.* from district a,city b where a.pcode=b.id and a.id=" + aCode;
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "UTF-8");
                CityMode city = new CityMode();
                city.setName(name);
                city.setCountryID(code);
                list.add(city);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(2);
            String name = new String(bytes, "UTF-8");
            CityMode city = new CityMode();
            city.setName(name);
            city.setCountryID(code);
            list.add(city);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        //	list.add(new CityMode());

        return list.get(0);
    }

    /**
     * 根据城市获取所属省
     * @param cCode
     * @return
     */
    public CityMode getProvinceByCity(String cCode) {
        List<CityMode> list = new ArrayList<CityMode>();
        //	list.add(new CityMode());
        Cursor cursor = null;
        try {
            String sql = "select b.* from city a,province b where a.pcode=b.id and a.id=" + cCode;
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "UTF-8");
                CityMode city = new CityMode();
                city.setName(name);
                city.setCountryID(code);
                list.add(city);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(2);
            String name = new String(bytes, "UTF-8");
            CityMode city = new CityMode();
            city.setName(name);
            city.setCountryID(code);
            list.add(city);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        //	list.add(new CityMode());

        return list.get(0);
    }


}
