package com.paul.shelton.measureit.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.paul.shelton.measureit.classes.Measurement;
import com.paul.shelton.measureit.models.Bill;
import com.paul.shelton.measureit.models.Customer;
import com.paul.shelton.measureit.models.Order;
import com.paul.shelton.measureit.models.OrderMeasurement;
import com.paul.shelton.measureit.models.Pant;
import com.paul.shelton.measureit.models.Shirt;

/**
 * Created by ecom-shelton.paul on 08/07/17.
 */
public class DBUtility extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "measureIt.db";
    private static final int DATABASE_VERSION = 8;
    private Customer customer = new Customer();
    private Pant pant = new Pant();
    private Shirt shirt = new Shirt();
    private Order order = new Order();
    private Bill bill = new Bill();
    private OrderMeasurement orderMeasurement = new OrderMeasurement();

    public DBUtility(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        Customer.CreateTable(db);
//        pant.CreateTable(db);
//        shirt.CreateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        customer.Upgrade(db,oldVersion,newVersion);
        pant.Upgrade(db,oldVersion,newVersion);
        shirt.Upgrade(db,oldVersion,newVersion);
        order.Upgrade(db,oldVersion,newVersion);
        orderMeasurement.Upgrade(db,oldVersion,newVersion);
        bill.Upgrade(db,oldVersion,newVersion);
//        onCreate(db);
    }

    public Long insertCustomer(String name, String email, String phone) {
        SQLiteDatabase db = getWritableDatabase();
        return customer.insertCustomer(db,name,email,phone);
    }

    public boolean updateCustomer(Integer id, String name, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        return customer.updateCustomer(db,id,name,email,phone);
    }

    public Cursor getCustomer(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return customer.getCustomer(db,id);
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return customer.getAllCustomers(db);
    }

    public Cursor getShirtsbyCustomerId(Long customer_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return shirt.getShirtsbyCustomerId(db,customer_id);
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

    public boolean updatePant(Long id, String pleat,String belt_loop,String back_pocket,String waist,String pocket_type,Boolean ticket_pocket,Boolean side_stitch,Boolean botton_zip,Long customer_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return pant.updatePant(db,id,pleat,belt_loop,back_pocket,waist,pocket_type,ticket_pocket,side_stitch,botton_zip,customer_id);
    }

    public Cursor getPant(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return pant.getPant(db,id);
    }

    public Cursor getAllPants() {
        SQLiteDatabase db = this.getReadableDatabase();
        return pant.getAllPants(db);
    }

    public Cursor getPantsbyCustomerId(Long customer_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return pant.getPantsByCustomerId(db,customer_id);
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

    public boolean updateShirt(Long id, String collor, String pocket, String hand_cuff, String shirt_style, String shoulder_style, String shirt_type, String mundah, Boolean patti, Boolean back_pleat, Boolean hand_fold, Long customer_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return shirt.updateShirt(db,id,collor,pocket,hand_cuff,shirt_style,shoulder_style,shirt_type,mundah,patti,back_pleat,hand_fold,customer_id);
    }

    public Cursor getShirt(Long id) {
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

    public Long getOrCreateOrder(Long customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor rs = order.getPendingOrderByCustomerId(db,customerId);

        if (rs.getCount() !=0) {

            rs.moveToFirst();
            int id = rs.getInt(0);
//            if (rs.getCount() > 0) {
//                do {
//
//
//                    Log.v("OrderId", id + "");
//                    Log.v("OrderId", rs.getString(1) + "");
//
//                } while (rs.moveToNext());
//                if (!rs.isClosed()) {
//                    rs.close();
//                }
//                return Long.valueOf(1);
////            rs.moveToFirst();
            Log.v("OrderId", id + "");
            return Long.valueOf(id);

        }
        return order.insertOrder(db,customerId);
    }

    public Long addMeasurementtoOrder(Long orderId, Long measurementId,String type,String style) {
        SQLiteDatabase db = this.getWritableDatabase();
        Long id = orderMeasurement.insertOrderMeasurement(db,orderId,measurementId,1,type,style);
        return id;
    }

    public boolean orderMeasurementExists(Long orderId, Long measurementId,String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor rs = orderMeasurement.getOrderMeasurements(db,orderId,measurementId,type);

        if (!(rs.moveToFirst()) || rs.getCount() ==0){
            rs.moveToFirst();
            return false;
        }
        return true;
    }

    public Cursor getOrderDetails(Long orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return order.getOrderById(db,orderId);
    }

    public Cursor getOrderMeasurements(Long orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return orderMeasurement.getOrderMeasurements(db,orderId);
    }


    public boolean updateOrderMeasurement(int ordersMeasuremenetId, Integer quantity) {
        SQLiteDatabase db = this.getReadableDatabase();
        return orderMeasurement.updateOrderMeasurement(db, (long) ordersMeasuremenetId,quantity);
    }
}
