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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.adapters.OrderMeasurementAdapter;
import com.paul.shelton.measureit.classes.Customer;
import com.paul.shelton.measureit.classes.Measurement;
import com.paul.shelton.measureit.classes.OrderMeasurement;
import com.paul.shelton.measureit.utils.DBUtility;

import java.util.ArrayList;
import java.util.Collections;

public class OrderActivity extends AppCompatActivity {
    private DBUtility dbUtility;
    private Long CustomerId;
    private Long OrderId;
    private String Status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
        dbUtility = new DBUtility(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle b = getIntent().getExtras();
        if (b!= null) {
            OrderId = b.getLong("order_id");

            Cursor rs = dbUtility.getOrderDetails(OrderId);
            rs.moveToFirst();

            String Status = rs.getString(1);
            String txt = rs.getString(1);
            txt = txt + rs.getString(2);
            txt = txt + rs.getString(3);
            txt = txt + rs.getString(4);
            txt = txt + rs.getInt(5);

            CustomerId = Long.valueOf(rs.getInt(5));
            Log.v("Orders", txt);
            if (!rs.isClosed()) {
                rs.close();
            }
            ArrayList<OrderMeasurement> orderMeasurementList = new ArrayList<OrderMeasurement>();

            Cursor ms = dbUtility.getOrderMeasurements(OrderId);
            ms.moveToFirst();
            String txts = "";
            do {

                txts = txts + ms.getString(0);
                txts = txts + " -- " + ms.getString(1);
                txts = txts + " -- " + ms.getString(2);
                txts = txts + " -- " + ms.getString(3);
                txts = txts + " -- " + ms.getString(4);
                txts = txts + " -- " + ms.getString(5);
                txts = txts + " -- " + ms.getString(6);
                txts = txts + " -- " + ms.getString(7);
                txts = txts + "\n";

                int id = ms.getInt(0);
                String created_date = ms.getString(1);
//                txts = txts + " -- " + ms.getString(2);
//                txts = txts + " -- " + ms.getString(3);
//                txts = txts + " -- " + ms.getString(4);
                String type =  ms.getString(5);
                String style = ms.getString(6);
                int quantity  = ms.getInt(7);
                txts = txts + "\n";


                Measurement measurement = new Measurement(id, type, style, created_date);
                orderMeasurementList.add(new OrderMeasurement(measurement,id,quantity));


            } while (ms.moveToNext());
            Log.v("MeasurementIds", txts);
            if (!ms.isClosed()) {
                ms.close();
            }

            Collections.sort(orderMeasurementList);
            OrderMeasurementAdapter adapter = new OrderMeasurementAdapter(this, orderMeasurementList);

            final ListView listView = (ListView) findViewById(R.id.order_measurement_list);
            listView.setAdapter(adapter);


            final Context cont = this;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Log.v("position",position+"");
                }
            });

            final Button save_order = (Button) findViewById(R.id.save_order);
            save_order.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   saveOrderMeasurement(listView);
                    gotoCustomerDetails();
                }
            });

            final Button submit_order = (Button) findViewById(R.id.submit_order);
            submit_order.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    saveOrderMeasurement(listView);
                    Log.v("Order Submitted","Order Submitted");
                }
            });


        }
    }

    private void saveOrderMeasurement(ListView listView) {
        OrderMeasurementAdapter oma = (OrderMeasurementAdapter)listView.getAdapter();
        for(int i=0; i < oma.getCount();i++)
        {
            View view = listView.getChildAt(i);
            String quantity = ((EditText)view.findViewById(R.id.order_measurement_list_quantity)).getText().toString();

            OrderMeasurement om = (OrderMeasurement) oma.getItem(i);
            dbUtility.updateOrderMeasurement(om.ordersMeasuremenetId, Integer.valueOf(quantity));
            Log.v("om.ordersMeasuremenetId",om.ordersMeasuremenetId+"");
            Log.v("om.quantity",quantity+"");

        }
        Log.v("Order Saved","Order Saved");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//                onBackPressed();
                gotoCustomerDetails();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gotoCustomerDetails()
    {
        Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
        Bundle b = new Bundle();
        b.putLong("customer_id", this.CustomerId);
        intent.putExtras(b);
        startActivity(intent);
    }

}
