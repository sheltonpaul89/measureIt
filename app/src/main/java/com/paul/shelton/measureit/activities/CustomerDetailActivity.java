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
import android.widget.TextView;

import com.paul.shelton.measureit.Models.Customer;
import com.paul.shelton.measureit.R;

public class CustomerDetailActivity extends AppCompatActivity {
    private Long Customer_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        if (b!= null) {
            String name = b.getString("name");
            String email = b.getString("email");
            String phone = b.getString("phone");
            Customer_Id = b.getLong("customer_id");
            ((TextView) findViewById(R.id.detail_name)).setText("Name : " + name);
            ((TextView) findViewById(R.id.detail_email)).setText("Email : " + email);
            ((TextView) findViewById(R.id.detail_phone)).setText("Phone : " + phone);
        }
        final Context cont = this;
        final Button button = (Button) findViewById(R.id.add_measurement);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateMeasurement.class);
                Bundle b = new Bundle();
                b.putLong("customerId", Customer_Id);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }

}
