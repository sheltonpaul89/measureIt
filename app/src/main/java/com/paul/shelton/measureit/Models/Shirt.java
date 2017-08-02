package com.paul.shelton.measureit.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ecom-shelton.paul on 08/07/17.
 */
public class Shirt {
    public static final String DATABASE_NAME = "measureIt.db";
    private static final int DATABASE_VERSION = 1;
    public static final String SHIRT_TABLE = "shirt";
    public static final String SHIRT_ID = "_id";
    public static final String COLLOR = "collor";
    public static final String POCKET = "pocket" ;
    public static final String HAND_CUFF = "hand_cuff";
    public static final String SHIRT_STYLE = "shirt_style";
    public static final String SHOULDER_STYLE = "shoulder_style";
    public static final String SHIRT_TYPE = "shirt_type";
    public static final String MUNDAH = "mundah";
    public static final String PATTI = "patti";
    public static final String BACK_PLEAT = "back_pleat";
    public static final String HAND_FOLD = "hand_fold";
    public static final String CREATED_DATE ="created_date";
    public static final String MODIFIED_DATE = "modified_date";
    public static final String CUSTOMER_ID = "customer_id";

//    public Shirt(Context context) {
//        super(context, DATABASE_NAME , null, DATABASE_VERSION);
//    }

    public void CreateTable(SQLiteDatabase db) {
        Log.v("Create application Table",SHIRT_TABLE );
        db.execSQL("CREATE TABLE " + SHIRT_TABLE + "(" +
                SHIRT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLLOR + " TEXT, " +
                POCKET + " TEXT, " +
                HAND_CUFF + " TEXT, " +
                SHIRT_STYLE + " TEXT, " +
                SHOULDER_STYLE + " TEXT, " +
                SHIRT_TYPE + " TEXT, " +
                MUNDAH + " TEXT, "+
                PATTI + " BOOLEAN, " +
                BACK_PLEAT + " BOOLEAN, " +
                HAND_FOLD + " BOOLEAN, " +
                CREATED_DATE + " TEXT, " +
                MODIFIED_DATE + " TEXT, " +
                CUSTOMER_ID + " INTEGER)"
        );
    }

    public void Upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SHIRT_TABLE);
        CreateTable(db);
    }

    public Long insertShirt(SQLiteDatabase db,String collor,String pocket,String hand_cuff,String shirt_style,String shoulder_style,String shirt_type,String mundah,Boolean patti,Boolean back_pleat,Boolean hand_fold,Long customer_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLOR, collor);
        contentValues.put(POCKET, pocket);
        contentValues.put(HAND_CUFF, hand_cuff);
        contentValues.put(SHIRT_STYLE, shirt_style);
        contentValues.put(SHOULDER_STYLE, shoulder_style);
        contentValues.put(SHIRT_TYPE, shirt_type);
        contentValues.put(MUNDAH, mundah);
        contentValues.put(PATTI, patti);
        contentValues.put(BACK_PLEAT, back_pleat);
        contentValues.put(HAND_FOLD, hand_fold);

        contentValues.put(CUSTOMER_ID,customer_id);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        contentValues.put(CREATED_DATE, dateFormat.format(date));
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));

        long id = db.insert(SHIRT_TABLE, null, contentValues);
        Log.v("Shirt Inserted ",(Long.toString(id)));
        return id;
    }

    public boolean updateShirt(SQLiteDatabase db,Long id,String collor,String pocket,String hand_cuff,String shirt_style,String shoulder_style,String shirt_type,String mundah,Boolean patti,Boolean back_pleat,Boolean hand_fold,Long customer_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLOR, collor);
        contentValues.put(POCKET, pocket);
        contentValues.put(HAND_CUFF, hand_cuff);
        contentValues.put(SHIRT_STYLE, shirt_style);
        contentValues.put(SHOULDER_STYLE, shoulder_style);
        contentValues.put(SHIRT_TYPE, shirt_type);
        contentValues.put(MUNDAH, mundah);
        contentValues.put(PATTI, patti);
        contentValues.put(BACK_PLEAT, back_pleat);
        contentValues.put(HAND_FOLD, hand_fold);
        contentValues.put(CUSTOMER_ID,customer_id);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));

        db.update(SHIRT_TABLE, contentValues, SHIRT_ID + " = ? ", new String[] { Long.toString(id) } );
        Log.v("Shirt Measurements Updated ",  (Long.toString(id)));
        return true;
    }

    public Cursor getShirt(SQLiteDatabase db,Long id) {
        Cursor res = db.rawQuery( "SELECT * FROM " + SHIRT_TABLE + " WHERE " +
                SHIRT_ID + "=?", new String[] { Long.toString(id) } );
        return res;
    }

    public Cursor getshirtsByCustomerId(SQLiteDatabase db,int id) {
        Cursor res = db.rawQuery( "SELECT * FROM " + SHIRT_TABLE + " WHERE " +
                CUSTOMER_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }

    public Cursor getAllShirts(SQLiteDatabase db) {
        Cursor res = db.rawQuery( "SELECT * FROM " + SHIRT_TABLE, null );
        return res;
    }

    public Cursor getShirtsbyCustomerId(SQLiteDatabase db,Long customerId) {
        Cursor res = db.rawQuery( "SELECT * FROM " + SHIRT_TABLE +" WHERE CUSTOMER_ID = "+customerId, null );
        return res;
    }

    public Integer deleteShirt(SQLiteDatabase db,Integer id) {
        return db.delete(SHIRT_TABLE,
                SHIRT_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
