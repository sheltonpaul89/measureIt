package com.paul.shelton.measureit.classes;

import com.paul.shelton.measureit.utils.DateUtility;

import java.util.Date;

/**
 * Created by ecom-shelton.paul on 22/07/17.
 */
public class OrderMeasurement implements Comparable<OrderMeasurement>{
    public Measurement measuremenet;
    public int ordersMeasuremenetId;
    public int Quantity;
    public OrderMeasurement(Measurement measuremenet,int ordersMeasuremenetId,int Quantity)
    {
        this.measuremenet = measuremenet;
        this.ordersMeasuremenetId = ordersMeasuremenetId;
        this.Quantity = Quantity;
    }

    @Override
    public int compareTo(OrderMeasurement orderMeasurement) {
        return orderMeasurement.measuremenet.createdDate.compareTo(this.measuremenet.createdDate);
    }
}
