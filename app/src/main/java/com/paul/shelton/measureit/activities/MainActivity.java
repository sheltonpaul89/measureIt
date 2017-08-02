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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.common.enums.MainEnum;
import com.paul.shelton.measureit.utils.EnumUtility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, EnumUtility.getEnumString(MainEnum.class));
        final Context cont = this;
        final ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String optionText=(String)parent.getItemAtPosition(position);
                Log.v("SelectedOption", optionText);
                MainEnum option = MainEnum.valueOf(optionText.replace(" ","_"));
                Intent intent=null;
                switch (option)
                {
                    case NEW_CUSTOMER:
                        intent = new Intent(cont, CreateCustomerActivity.class);
                        break;
                    case CUSTOMER_SEARCH:
                        intent = new Intent(cont, CustomerListActivity.class);
                        break;
                    case ORDER_STATUS:
                        break;
                    case DELIVERY_DATES:
                        break;
                }
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
