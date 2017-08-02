package com.paul.shelton.measureit.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.paul.shelton.measureit.common.enums.OrderStatusEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ecom-shelton.paul on 08/07/17.
 */
public class Order {
    public static final String DATABASE_NAME = "measureIt.db";
    private static final int DATABASE_VERSION = 1;
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID = "_id";
    public static final String STATUS = "Status";
    public static final String CREATED_DATE ="created_date";
    public static final String MODIFIED_DATE = "modified_date";
    public static final String DELIVERY_DATE = "delivery_date";
    public static final String CUSTOMER_ID = "customer_id";


    public void CreateTable(SQLiteDatabase db) {
        Log.v("Create application Table",ORDER_TABLE );
        db.execSQL("CREATE TABLE " + ORDER_TABLE + "(" +
                ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STATUS + " TEXT, " +
                CREATED_DATE + " TEXT, " +
                MODIFIED_DATE + " TEXT, " +
                DELIVERY_DATE + " TEXT, " +
                CUSTOMER_ID + " INTEGER)"
        );
    }

    public void Upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("oldVersion",oldVersion+"");
        Log.v("newVersion",newVersion+"");
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
//        }
//        catch (Exception ex) {
//            Log.v("Exception","Exception while Dropping Order");
//        }
        CreateTable(db);
    }

    public Long insertOrder(SQLiteDatabase db,Long customer_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_ID,customer_id);
        contentValues.put(STATUS, OrderStatusEnum.PENDING.toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        contentValues.put(CREATED_DATE, dateFormat.format(date));
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));

        long id = db.insert(ORDER_TABLE, null, contentValues);
        Log.v("Order Inserted ",(Long.toString(id)));
        return id;
    }

    public boolean updateOrder(SQLiteDatabase db,Long id,String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS,status);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));

        db.update(ORDER_TABLE, contentValues, ORDER_ID + " = ? ", new String[] { Long.toString(id) } );
        Log.v("Order Status Updated ",  (Long.toString(id)));
        return true;
    }

    public Cursor getOrderById(SQLiteDatabase db,Long id) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDER_TABLE + " WHERE " +
                ORDER_ID + "=?", new String[] { Long.toString(id) } );
        return res;
    }

    public Cursor getPendingOrderByCustomerId(SQLiteDatabase db,Long customerId) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDER_TABLE + " WHERE "+ STATUS +"='"+ OrderStatusEnum.PENDING.toString() +"' AND " +
                CUSTOMER_ID + "=?", new String[] { Long.toString(customerId) } );
        return res;
    }

    public Cursor getOrdersByCustomerId(SQLiteDatabase db,Long customerId,String status) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDER_TABLE + " WHERE "+ STATUS +"='"+ status  +"' AND " +
                CUSTOMER_ID + "=?", new String[] { Long.toString(customerId) } );
        return res;
    }

    public Cursor getOrdersByCustomerId(SQLiteDatabase db,Long customerId) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDER_TABLE + " WHERE " +
                CUSTOMER_ID + "=?", new String[] { Long.toString(customerId) } );
        return res;
    }


    public Cursor getAllOrders(SQLiteDatabase db) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDER_TABLE, null );
        return res;
    }

    public Cursor getOrdersbyCustomerId(SQLiteDatabase db,Long customerId) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDER_TABLE +" WHERE ORDER_ID = "+customerId, null );
        return res;
    }

    public Integer deleteOrder(SQLiteDatabase db,Integer id) {
        return db.delete(ORDER_TABLE,
                ORDER_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
