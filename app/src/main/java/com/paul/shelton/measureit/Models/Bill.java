package com.paul.shelton.measureit.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.paul.shelton.measureit.common.enums.BillStatusEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ecom-shelton.paul on 08/07/17.
 */
public class Bill {
    public static final String DATABASE_NAME = "measureIt.db";
    private static final int DATABASE_VERSION = 1;
    public static final String BILL_TABLE = "bill";
    public static final String BILL_ID = "_id";
    public static final String STATUS = "Status";
    public static final String CREATED_DATE ="created_date";
    public static final String MODIFIED_DATE = "modified_date";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String ORDER_ID = "order_id";


    public void CreateTable(SQLiteDatabase db) {
        Log.v("Create application Table",BILL_TABLE );
        db.execSQL("CREATE TABLE " + BILL_TABLE + "(" +
                BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STATUS + " TEXT, " +
                CREATED_DATE + " TEXT, " +
                MODIFIED_DATE + " TEXT, " +
                ORDER_ID + " INTEGER, " +
                CUSTOMER_ID + " INTEGER)"
        );
    }

    public void Upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BILL_TABLE);
        CreateTable(db);
    }

    public Long insertBill(SQLiteDatabase db,Long customer_id,Long order_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_ID,customer_id);
        contentValues.put(ORDER_ID,order_id);
        contentValues.put(STATUS,BillStatusEnum.UNPAID.toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        contentValues.put(CREATED_DATE, dateFormat.format(date));
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));

        long id = db.insert(BILL_TABLE, null, contentValues);
        Log.v("Bill Inserted ",(Long.toString(id)));
        return id;
    }

    public boolean updateBill(SQLiteDatabase db,Long id,String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS,status);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));

        db.update(BILL_TABLE, contentValues, BILL_ID + " = ? ", new String[] { Long.toString(id) } );
        Log.v("Bill Status Updated ",  (Long.toString(id)));
        return true;
    }

    public Cursor getBillById(SQLiteDatabase db,Long id) {
        Cursor res = db.rawQuery( "SELECT * FROM " + BILL_TABLE + " WHERE " +
                BILL_ID + "=?", new String[] { Long.toString(id) } );
        return res;
    }

    public Cursor getPendingBillByCustomerId(SQLiteDatabase db,Long customerId) {
        Cursor res = db.rawQuery( "SELECT * FROM " + BILL_TABLE + " WHERE "+ STATUS +"='"+ BillStatusEnum.UNPAID +"' AND " +
                CUSTOMER_ID + "=?", new String[] { Long.toString(customerId) } );
        return res;
    }

    public Cursor getBillsByCustomerId(SQLiteDatabase db,Long customerId,String status) {
        Cursor res = db.rawQuery( "SELECT * FROM " + BILL_TABLE + " WHERE "+ STATUS +"='"+ status  +"' AND " +
                CUSTOMER_ID + "=?", new String[] { Long.toString(customerId) } );
        return res;
    }

    public Cursor getAllBills(SQLiteDatabase db) {
        Cursor res = db.rawQuery( "SELECT * FROM " + BILL_TABLE, null );
        return res;
    }

    public Cursor getBillsbyCustomerId(SQLiteDatabase db,Long customerId) {
        Cursor res = db.rawQuery( "SELECT * FROM " + BILL_TABLE +" WHERE BILL_ID = "+customerId, null );
        return res;
    }

    public Integer deleteBill(SQLiteDatabase db,Integer id) {
        return db.delete(BILL_TABLE,
                BILL_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
