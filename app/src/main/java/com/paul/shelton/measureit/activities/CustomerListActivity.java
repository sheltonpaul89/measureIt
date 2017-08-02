package com.paul.shelton.measureit.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.adapters.CustomerAdapter;
import com.paul.shelton.measureit.classes.Customer;
import com.paul.shelton.measureit.common.enums.CustomerEnum;
import com.paul.shelton.measureit.utils.DBUtility;
import com.paul.shelton.measureit.utils.EnumUtility;

import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {

    private DBUtility dbUtility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        dbUtility = new DBUtility(this);
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        Cursor rs = dbUtility.getAllCustomers();
        rs.moveToFirst();

        if(rs.getCount()==0)
            return;
        do {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String email = rs.getString(2);
            String phone = rs.getString(3)+"   ";

            Customer customer = new Customer(id,email,name,phone);
            customerList.add(customer);
        }while (rs.moveToNext());

        if (!rs.isClosed())  {
            rs.close();
        }

        CustomerAdapter adapter = new CustomerAdapter(this, customerList);

        ListView listView = (ListView) findViewById(R.id.customer_list);
        listView.setAdapter(adapter);

        final Context cont = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Customer customer = (Customer) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
                Bundle b = new Bundle();
                b.putLong("customer_id", customer.id);
                intent.putExtras(b);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
