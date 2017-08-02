package com.paul.shelton.measureit.classes;

import com.paul.shelton.measureit.utils.DateUtility;

import java.util.Date;

/**
 * Created by ecom-shelton.paul on 22/07/17.
 */
public class Measurement  implements Comparable<Measurement>{
    public String type;
    public String style;
    public String displayDate;
    public Date createdDate;
    public int id;
    public Measurement(int id, String type, String style, String createdDate)
    {
        this.type = type;
        this.style = style;
        this.displayDate = createdDate +"   ";
        this.createdDate = DateUtility.getDate(createdDate);
        this.id = id;
    }

    @Override
    public int compareTo(Measurement measurement) {
        return measurement.createdDate.compareTo(this.createdDate);
    }
}
