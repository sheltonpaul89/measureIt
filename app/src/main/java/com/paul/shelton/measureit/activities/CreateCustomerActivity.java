package com.paul.shelton.measureit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.utils.DBUtility;

public class CreateCustomerActivity extends AppCompatActivity {
    DBUtility dbUtility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbUtility = new DBUtility(this);
        setContentView(R.layout.activity_create_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Context cont = this;
        final Button button = (Button) findViewById(R.id.createCustomer);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.customerName)).getText().toString();
                String email = ((EditText)findViewById(R.id.customerEmail)).getText().toString();
                String phone = ((EditText)findViewById(R.id.customerPhno)).getText().toString();
                Log.v(name,email);
                Log.v("Phone no",phone);
                insertCustomer(name,email,phone);
            }
        });
    }

    public void insertCustomer( String name, String email, String phone) {
            Long customerID = dbUtility.insertCustomer(name,email,phone);
            Toast.makeText(getApplicationContext(), "Customer Inserted : "+ Long.toString(customerID) , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
            Bundle b = new Bundle();
            b.putString("name", name);
            b.putString("email", email);
            b.putString("phone", phone);
            b.putLong("customer_id", customerID);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
