package com.paul.shelton.measureit.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.paul.shelton.measureit.classes.Measurement;

import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.adapters.MeasurementAdapter;
import com.paul.shelton.measureit.utils.DBUtility;

import java.util.ArrayList;
import java.util.Collections;

public class CustomerDetailActivity extends AppCompatActivity {
    private Long Customer_Id;
    private DBUtility dbUtility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        dbUtility = new DBUtility(this);

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
        Bundle b = getIntent().getExtras();
        if (b!= null) {
            Customer_Id = b.getLong("customer_id");


            Cursor rs = dbUtility.getAllCustomers();
            rs.moveToFirst();

            String name = rs.getString(1);
            String email = rs.getString(2);
            String phone = rs.getString(3);


            while (rs.moveToNext()) {
                if(rs.getInt(0) == Customer_Id)
                {
                    name = rs.getString(1);
                    email = rs.getString(2);
                    phone = rs.getString(3);
                    break;
                }
            }

            if (!rs.isClosed())  {
                rs.close();
            }
            ((TextView) findViewById(R.id.detail_name)).setText("Name : " + name);
            ((TextView) findViewById(R.id.detail_email)).setText("Email : " + email);
            ((TextView) findViewById(R.id.detail_phone)).setText("Phone : " + phone);
        }
        final Context cont = this;
        final Button button = (Button) findViewById(R.id.add_measurement);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeasurementActivity.class);
                Bundle b = new Bundle();
                Log.v("customerId", Long.toString(Customer_Id));
                b.putLong("customerId", Customer_Id);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        // List View for the Measurements ...

        ArrayList<Measurement> measurementList = new ArrayList<Measurement>();
        Cursor rs = dbUtility.getShirtsbyCustomerId(Customer_Id);
        rs.moveToFirst();

        Cursor rp = dbUtility.getPantsbyCustomerId(Customer_Id);
        rp.moveToFirst();
        if(rs.getCount()>0) {
            do {
                int id = rs.getInt(0);
                String type = "Shirt";
                String style = rs.getString(6);
                String created_date = rs.getString(11) + "   ";

                Measurement measurement = new Measurement(id, type, style, created_date);
                measurementList.add(measurement);
            } while (rs.moveToNext());
        }
        if(rp.getCount()>0) {
            do {
                int id = rp.getInt(0);
                String type = "Pant";
                String style = "Full";
                String created_date = rp.getString(9) + "   ";

                Measurement measurement = new Measurement(id, type, style, created_date);
                measurementList.add(measurement);
            } while (rp.moveToNext());
        }
        if (!rp.isClosed())  {
            rp.close();
        }
        if (!rs.isClosed())  {
            rs.close();
        }

        Collections.sort(measurementList);
        MeasurementAdapter adapter = new MeasurementAdapter(this, measurementList);

        ListView listView = (ListView) findViewById(R.id.measurement_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Measurement measurement = (Measurement) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), MeasurementActivity.class);
                Bundle b = new Bundle();
                b.putLong("measurementId", measurement.id);
                b.putString("type", measurement.type);
                b.putLong("customerId", Customer_Id);

                intent.putExtras(b);
                startActivity(intent);

            }
        });


    }

}
