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
public class OrderMeasurement {
    public static final String DATABASE_NAME = "measureIt.db";
    private static final int DATABASE_VERSION = 1;
    public static final String ORDERMEASUREMENT_TABLE = "ordermeasurement";
    public static final String ORDERMEASUREMENT_ID = "_id";
    public static final String MEASUREMENT_ID = "measurement_id";
    public static final String CREATED_DATE ="created_date";
    public static final String MODIFIED_DATE = "modified_date";
    public static final String QUANTITY = "quantity";
    public static final String TYPE = "type";
    public static final String STYLE = "style";
    public static final String ORDER_ID = "order_id";


    public void CreateTable(SQLiteDatabase db) {
        Log.v("Create application Table",ORDERMEASUREMENT_TABLE );
        db.execSQL("CREATE TABLE " + ORDERMEASUREMENT_TABLE + "(" +
                ORDERMEASUREMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CREATED_DATE + " TEXT, " +
                MODIFIED_DATE + " TEXT, " +
                MEASUREMENT_ID + " INTEGER, "+
                QUANTITY + " INTEGER, "+
                TYPE + " TEXT, " +
                STYLE + " TEXT, " +
                ORDER_ID + " INTEGER)"
        );
    }

    public void Upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ORDERMEASUREMENT_TABLE);
        CreateTable(db);
    }

    public Long insertOrderMeasurement(SQLiteDatabase db,Long order_id,Long measurement_id,int quantity,String type,String style) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_ID,order_id);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(MEASUREMENT_ID, measurement_id);
        contentValues.put(TYPE, type);
        contentValues.put(STYLE, style);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        contentValues.put(CREATED_DATE, dateFormat.format(date));
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));

        long id = db.insert(ORDERMEASUREMENT_TABLE, null, contentValues);
        Log.v("Order Measurement Inserted ",(Long.toString(id)));
        return id;
    }

    public boolean updateOrderMeasurement(SQLiteDatabase db,Long id,int quantity) {
        ContentValues contentValues = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));
        contentValues.put(QUANTITY,quantity);

        db.update(ORDERMEASUREMENT_TABLE, contentValues, ORDERMEASUREMENT_ID + " = ? ", new String[] { Long.toString(id) } );
        Log.v("Order Measurement Updated ",  (Long.toString(id))+" : "+quantity);
        return true;
    }

    public Cursor getOrderMeasurementsByOrderId(SQLiteDatabase db,Long order_id) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDERMEASUREMENT_TABLE + " WHERE " +
                ORDER_ID + "=?", new String[] { Long.toString(order_id) } );
        return res;
    }

    public Integer deleteOrderMeasurement(SQLiteDatabase db,Integer id) {
        return db.delete(ORDERMEASUREMENT_TABLE,
                ORDERMEASUREMENT_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Cursor getOrderMeasurements(SQLiteDatabase db, Long orderId, Long measurementId) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDERMEASUREMENT_TABLE + " WHERE " +
                ORDER_ID + "=? AND "+ MEASUREMENT_ID +"=?", new String[] { Long.toString(orderId),Long.toString(measurementId) });
        return res;
    }

    public Cursor getOrderMeasurements(SQLiteDatabase db, Long orderId) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDERMEASUREMENT_TABLE + " WHERE " +
                ORDER_ID + "=?", new String[] { Long.toString(orderId) });
        return res;
    }

    public Cursor getOrderMeasurements(SQLiteDatabase db, Long orderId, Long measurementId, String type) {
        Cursor res = db.rawQuery( "SELECT * FROM " + ORDERMEASUREMENT_TABLE + " WHERE " +
                ORDER_ID + "=? AND "+ MEASUREMENT_ID +"=? AND "+ TYPE +"=?", new String[] { Long.toString(orderId),Long.toString(measurementId),type });
        return res;
    }
}
