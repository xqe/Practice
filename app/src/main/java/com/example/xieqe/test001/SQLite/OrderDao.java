package com.example.xieqe.test001.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieqe on 2017/9/15.
 */

public class OrderDao {

    //列定义
    private final String[] ORDER_COLUMNS = new String[]{"Id","CustomName","Price","Country"};
    private Context context;
    private OrderDBHelper orderDBHelper;

    public OrderDao(Context context) {
        this.context = context;
        orderDBHelper = new OrderDBHelper(context);
    }

    /**判断表中是否有数据*/
    public boolean isEmpty(){
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try{
            db = orderDBHelper.getReadableDatabase();
            cursor = db.query(OrderDBHelper.TABLE_NAME,new String[]{"COUNT(Id)"},null,null,null,null,null);

            if (cursor.moveToFirst()){
                count = cursor.getInt(0);
            }
            if (count > 0) {
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    /**初始化数据*/
    public void initTable(){
        SQLiteDatabase db = null;

        try{
            db = orderDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + "(Id,CustomName,Price,Country) values (1,'coffee',100,'China')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + "(Id,CustomName,Price,Country) values (2,'car',9000,'USA')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + "(Id,CustomName,Price,Country) values (3,'house',10000,'China')");

            db.setTransactionSuccessful();
            Log.i("===", "initTable: success");

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db != null){
                db.endTransaction();
                db.close();
            }
        }
    }

    /**获取全部数据*/
    public List<Order> getAllData(){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = orderDBHelper.getReadableDatabase();
            cursor = db.query(OrderDBHelper.TABLE_NAME,ORDER_COLUMNS,null,null,null,null,null);
            if (cursor.getCount() > 0){
                List<Order> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    list.add(parseOrder(cursor));
                }
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor!=null){
                cursor.close();
            }
            if (db!=null){
                db.close();
            }
        }
        return null;
    }

    private Order parseOrder(Cursor cursor){
        Order order = new Order();
        order.Id = cursor.getInt(cursor.getColumnIndex("Id"));
        order.customName = cursor.getString(cursor.getColumnIndex("CustomName"));
        order.country = cursor.getString(cursor.getColumnIndex("Country"));
        order.price = cursor.getInt(cursor.getColumnIndex("Price"));
        return order;
    }

    /**插入一条数据*/
    public boolean insert(int id,String customName,String country,int price){
        SQLiteDatabase db = null;
        try {
            db = orderDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("Id",id);
            contentValues.put("CustomName",customName);
            contentValues.put("Price",price);
            contentValues.put("Country",country);

            db.insert(OrderDBHelper.TABLE_NAME,null,contentValues);
            db.setTransactionSuccessful();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db != null){
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**更新数据*/
    public boolean update(){
        SQLiteDatabase db = null;
        try {
            db = orderDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("Price",9999);
            //更新第二行的price
            db.update(OrderDBHelper.TABLE_NAME,contentValues,"Id = ?",new String[]{String.valueOf(2)});
            db.setTransactionSuccessful();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db != null){
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**删除数据*/
    public boolean delete(){
        SQLiteDatabase db = null;
        try {
            db = orderDBHelper.getWritableDatabase();
            db.beginTransaction();

            //删除第二行
            db.delete(OrderDBHelper.TABLE_NAME,"Id = ?",new String[]{String.valueOf(2)});
            db.setTransactionSuccessful();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db != null){
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**查询数据*/
    public List<Order> query(){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = orderDBHelper.getReadableDatabase();
            //查询CunstomName = car的数据
            cursor = db.query(
                    OrderDBHelper.TABLE_NAME,ORDER_COLUMNS,
                    "CustomName = ?",
                    new String[]{"car"},null,null,null);
            if (cursor.getCount() > 0) {
                List<Order> list = new ArrayList<>();
                while (cursor.moveToNext()){
                    list.add(parseOrder(cursor));
                }
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db != null){
                db.close();
            }
        }
        return null;
    }

}
