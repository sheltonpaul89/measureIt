package com.paul.shelton.measureit.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ecom-shelton.paul on 08/07/17.
 */
public class Customer  {
    public static final String DATABASE_NAME = "measureIt.db";
    private static final int DATABASE_VERSION = 1;
    public static final String CUSTOMER_TABLE = "Customer";
    public static final String CUSTOMER_ID = "_id";
    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_EMAIL = "email";
    public static final String CUSTOMER_PHONE = "phone";

//    public Customer(Context context) {
//        super(context, DATABASE_NAME , null, DATABASE_VERSION);
//    }

//    @Override
    public void CreateTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CUSTOMER_TABLE + "(" +
                CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CUSTOMER_NAME + " TEXT, " +
                CUSTOMER_EMAIL + " TEXT, " +
                CUSTOMER_PHONE + " TEXT)"
        );
    }

    public void Upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        CreateTable(db);
    }

    public Long insertCustomer(SQLiteDatabase db,String name, String email, String phone) {
//        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_NAME, name);
        contentValues.put(CUSTOMER_EMAIL, email);
        contentValues.put(CUSTOMER_PHONE, phone);
        Long id = db.insert(CUSTOMER_TABLE, null, contentValues);
        Log.v("Customer Inserted ", (Long.toString(id)));
        return id;
    }

    public boolean updateCustomer(SQLiteDatabase db,Integer id, String name, String email, String phone) {
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_NAME, name);
        contentValues.put(CUSTOMER_EMAIL, email);
        contentValues.put(CUSTOMER_PHONE, phone);
        db.update(CUSTOMER_TABLE, contentValues, CUSTOMER_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getCustomer(SQLiteDatabase db, Long id) {
//        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("Query","SELECT * FROM " + CUSTOMER_TABLE + " WHERE " +
                CUSTOMER_ID + "="+Long.toString(id));
        Cursor res = db.rawQuery( "SELECT * FROM " + CUSTOMER_TABLE + " WHERE " +
                CUSTOMER_ID + "="+Long.toString(id), null );
        return res;
    }
    public Cursor getAllCustomers(SQLiteDatabase db) {
//        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CUSTOMER_TABLE, null );
        return res;
    }

    public Integer deleteCustomer(SQLiteDatabase db,Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CUSTOMER_TABLE,
                CUSTOMER_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

}
