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
public class Pant {
    public static final String DATABASE_NAME = "measureIt.db";
    private static final int DATABASE_VERSION = 1;
    public static final String PANT_TABLE = "pant";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String PANT_ID = "_id";
    public static final String PLEAT = "pleat";
    public static final String BELT_LOOP = "belt_loop";
    public static final String BACK_POCKET = "back_pocket";
    public static final String WAIST = "waist";
    public static final String POCKET_TYPE = "pocket_type";
    public static final String TICKET_POCKET = "ticket_pocket";
    public static final String SIDE_STITCH = "side_stitch";
    public static final String BOTTOM_ZIP = "bottom_zip";
    public static final String CREATED_DATE = "created_date";
    public static final String MODIFIED_DATE = "modified_date";

//    public Pant(Context context) {
//        super(context, DATABASE_NAME , null, DATABASE_VERSION);
//    }

    public void CreateTable(SQLiteDatabase db) {
        Log.v("Create application Table",PANT_TABLE );
        db.execSQL("CREATE TABLE " + PANT_TABLE + "(" +
                PANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PLEAT + " TEXT, " +
                BELT_LOOP + " TEXT, " +
                BACK_POCKET + " TEXT, " +
                WAIST + " TEXT, " +
                POCKET_TYPE + " TEXT, " +
                TICKET_POCKET + " BOOLEAN, " +
                SIDE_STITCH + " BOOLEAN, " +
                BOTTOM_ZIP + " BOOLEAN, " +
                CREATED_DATE + " TEXT, " +
                MODIFIED_DATE + " TEXT, " +
                CUSTOMER_ID + " INTEGER)"
        );
    }

    public void Upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PANT_TABLE);
        CreateTable(db);
    }

    public Long insertPant(SQLiteDatabase db, String pleat, String belt_loop, String back_pocket, String waist, String pocket_type, Boolean ticket_pocket, Boolean side_stitch, Boolean botton_stitch, Long customer_id) {
//        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLEAT,pleat);
        contentValues.put(BELT_LOOP,belt_loop);
        contentValues.put(BACK_POCKET,back_pocket);
        contentValues.put(WAIST,waist);
        contentValues.put(POCKET_TYPE,pocket_type);
        contentValues.put(TICKET_POCKET,ticket_pocket);
        contentValues.put(SIDE_STITCH,side_stitch);
        contentValues.put(BOTTOM_ZIP,botton_stitch);
        contentValues.put(CUSTOMER_ID,customer_id);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        contentValues.put(CREATED_DATE, dateFormat.format(date));
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));

        long id = db.insert(PANT_TABLE, null, contentValues);
        Log.v("Pant Measurements Inserted ",  (Long.toString(id)));
        return id;
    }

    public boolean updatePant(SQLiteDatabase db,Long id,String pleat,String belt_loop,String back_pocket,String waist,String pocket_type,Boolean ticket_pocket,Boolean side_stitch,Boolean botton_zip,Long customer_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLEAT,pleat);
        contentValues.put(BELT_LOOP,belt_loop);
        contentValues.put(BACK_POCKET,back_pocket);
        contentValues.put(WAIST,waist);
        contentValues.put(POCKET_TYPE,pocket_type);
        contentValues.put(TICKET_POCKET,ticket_pocket);
        contentValues.put(SIDE_STITCH,side_stitch);
        contentValues.put(BOTTOM_ZIP,botton_zip);
        contentValues.put(CUSTOMER_ID,customer_id);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        contentValues.put(MODIFIED_DATE, dateFormat.format(date));


        db.update(PANT_TABLE, contentValues, PANT_ID + " = ? ", new String[] { Long.toString(id) } );
        Log.v("Pant Measurements Updated ",  (Long.toString(id)));
        return true;
    }

    public Cursor getPant(SQLiteDatabase db,Long id) {
//        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PANT_TABLE + " WHERE " +
                PANT_ID + "=?", new String[] { Long.toString(id) } );
        return res;
    }

    public Cursor getPantsByCustomerId(SQLiteDatabase db,Long id) {
//        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PANT_TABLE + " WHERE " +
                CUSTOMER_ID + "=?", new String[] { Long.toString(id) } );
        return res;
    }

    public Cursor getAllPants(SQLiteDatabase db) {
//        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PANT_TABLE, null );
        return res;
    }

    public Integer deletePant(SQLiteDatabase db,Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PANT_TABLE,
                PANT_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

}
