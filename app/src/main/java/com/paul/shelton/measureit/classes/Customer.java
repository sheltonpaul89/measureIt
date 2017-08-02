package com.paul.shelton.measureit.classes;

/**
 * Created by ecom-shelton.paul on 22/07/17.
 */
public class Customer {
    public String email;
    public String name;
    public String phone;
    public int id;
    public Customer(int id,String email,String name,String phone)
    {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.id = id;
    }
}
