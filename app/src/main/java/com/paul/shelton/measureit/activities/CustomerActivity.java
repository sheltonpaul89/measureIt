package com.paul.shelton.measureit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.common.enums.CustomerEnum;
import com.paul.shelton.measureit.utils.EnumUtility;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, EnumUtility.getEnumString(CustomerEnum.class));

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        final Context cont = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String optionText=(String)parent.getItemAtPosition(position);
                CustomerEnum option = CustomerEnum.valueOf(optionText.replace(" ","_"));
                switch (option)
                {
                    case CREATE:
                        Intent intent = new Intent(cont, CreateCustomerActivity.class);
                        startActivity(intent);
                        break;
                    case SEARCH:
                        break;
                }
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
