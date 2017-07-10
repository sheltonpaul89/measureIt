package com.paul.shelton.measureit.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.paul.shelton.measureit.Models.Customer;
import com.paul.shelton.measureit.Models.Pant;
import com.paul.shelton.measureit.Models.Shirt;

/**
 * Created by ecom-shelton.paul on 08/07/17.
 */
public class DBUtility extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "measureIt.db";
    private static final int DATABASE_VERSION = 2;
    private Customer customer = new Customer();
    private Pant pant = new Pant();
    private Shirt shirt = new Shirt();
    public DBUtility(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
//        Customer customer = new Customer();
//        Pant pant = new Pant();
//        Shirt shirt = new Shirt();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        customer.CreateTable(db);
        pant.CreateTable(db);
        shirt.CreateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        customer.Upgrade(db,oldVersion,newVersion);
        pant.Upgrade(db,oldVersion,newVersion);
        shirt.Upgrade(db,oldVersion,newVersion);
//        onCreate(db);
    }

    public long insertCustomer(String name, String email, String phone) {
        SQLiteDatabase db = getWritableDatabase();
        return customer.insertCustomer(db,name,email,phone);
    }

    public boolean updateCustomer(Integer id, String name, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        return customer.updateCustomer(db,id,name,email,phone);
    }

    public Cursor getCustomer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return customer.getCustomer(db,id);
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return customer.getAllCustomers(db);
    }

    public Integer deleteCustomer(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return customer.deleteCustomer(db,id);
    }


    // Pant Methods
    public long insertPant(String pleat,String belt_loop,String back_pocket,String waist,String pocket_type,Boolean ticket_pocket,Boolean side_stitch,Boolean botton_zip,Long customer_id) {
        SQLiteDatabase db = getWritableDatabase();
        return pant.insertPant(db,pleat,belt_loop,back_pocket,waist,pocket_type,ticket_pocket,side_stitch,botton_zip,customer_id);
    }

    public boolean updatePant(Integer id, String pleat,String belt_loop,String back_pocket,String waist,String pocket_type,Boolean ticket_pocket,Boolean side_stitch,Boolean botton_zip,Long customer_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return pant.updatePant(db,id,pleat,belt_loop,back_pocket,waist,pocket_type,ticket_pocket,side_stitch,botton_zip,customer_id);
    }

    public Cursor getPant(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return pant.getPant(db,id);
    }
    public Cursor getAllPants() {
        SQLiteDatabase db = this.getReadableDatabase();
        return pant.getAllPants(db);
    }

    public Integer deletePant(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return pant.deletePant(db,id);
    }

    // Shirt Methods
    public long insertShirt(String collor,String pocket,String hand_cuff,String shirt_style,String shoulder_style,String shirt_type,String mundah,Boolean patti,Boolean back_pleat,Boolean hand_fold,Long customer_id) {
        SQLiteDatabase db = getWritableDatabase();
        return shirt.insertShirt(db,collor,pocket,hand_cuff,shirt_style,shoulder_style,shirt_type,mundah,patti,back_pleat,hand_fold,customer_id);
    }

    public boolean updateShirt(Integer id, String collor,String pocket,String hand_cuff,String shirt_style,String shoulder_style,String shirt_type,String mundah,Boolean patti,Boolean back_pleat,Boolean hand_fold,Long customer_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return shirt.updateShirt(db,id,collor,pocket,hand_cuff,shirt_style,shoulder_style,shirt_type,mundah,patti,back_pleat,hand_fold,customer_id);
    }

    public Cursor getShirt(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return shirt.getShirt(db,id);
    }
    public Cursor getAllShirts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return shirt.getAllShirts(db);
    }

    public Integer deleteShirt(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return shirt.deleteShirt(db,id);
    }
}
