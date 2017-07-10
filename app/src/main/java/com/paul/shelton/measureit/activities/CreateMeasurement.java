    package com.paul.shelton.measureit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.paul.shelton.measureit.Listners.CustomOnItemSelectedListener;
import com.paul.shelton.measureit.Models.Customer;
import com.paul.shelton.measureit.Models.Pant;
import com.paul.shelton.measureit.Models.Shirt;
import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.utils.DBUtility;

import java.util.ArrayList;
import java.util.List;

    public class CreateMeasurement extends AppCompatActivity {
    private Spinner meausreType;
    private Button btnSubmit;
    private DBUtility dbUtility;
    private Long Customer_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbUtility = new DBUtility(this);
        setContentView(R.layout.activity_create_measurement);
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

        meausreType = (Spinner) findViewById(R.id.measurement_type);
        meausreType.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        Bundle b = getIntent().getExtras();
        Customer_Id = b.getLong("customer_id");

        final Context cont = this;
        final Button create_pant = (Button) findViewById(R.id.create_pant);
        create_pant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String pleat = ((EditText)findViewById(R.id.p_pleat)).getText().toString();
                String belt_loop = ((EditText)findViewById(R.id.p_beltloop)).getText().toString();
                String back_pocket = ((EditText)findViewById(R.id.p_backpocket)).getText().toString();
                String waist = ((EditText)findViewById(R.id.p_waist)).getText().toString();
                String pocket_type = ((AdapterView)findViewById(R.id.p_pocket_type)).getSelectedItem().toString();
                Boolean ticket_pocket = ((CheckBox)findViewById(R.id.p_ticket_pocket)).isChecked();
                Boolean side_stitch = ((CheckBox)findViewById(R.id.p_side_stitch)).isChecked();
                Boolean botton_zip = ((CheckBox)findViewById(R.id.p_bottom_zip)).isChecked();

                insertPant(pleat,belt_loop,back_pocket,waist,pocket_type,ticket_pocket,side_stitch,botton_zip,Customer_Id);
            }
        });

        final Button create_shirt = (Button) findViewById(R.id.create_shirt_fs);
        create_shirt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String collor = ((EditText)findViewById(R.id.s_collor)).getText().toString();
                String pocket = ((EditText)findViewById(R.id.s_pocket)).getText().toString();
                String hand_cuff = ((EditText)findViewById(R.id.s_hand_cuff)).getText().toString();
                String shirt_style = ((AdapterView)findViewById(R.id.s_style_type)).getSelectedItem().toString();
                String shoulder_style = ((AdapterView)findViewById(R.id.s_shoulder_type)).getSelectedItem().toString();
                String shirt_type = ((AdapterView)findViewById(R.id.s_shirt_type)).getSelectedItem().toString();
                String mundah = ((EditText)findViewById(R.id.s_mundah)).getText().toString();
                Boolean patti = ((CheckBox)findViewById(R.id.s_patti)).isChecked();
                Boolean back_pleat = ((CheckBox)findViewById(R.id.s_back_pleat)).isChecked();
                Boolean hand_fold = ((CheckBox)findViewById(R.id.s_hand_fold)).isChecked();

                insertShirt(collor,pocket,hand_cuff,shirt_style,shoulder_style,shirt_type,mundah,patti,back_pleat,hand_fold,Customer_Id);
            }
        });

    }

    public void insertPant(String pleat,String belt_loop,String back_pocket,String waist,String pocket_type,Boolean ticket_pocket,Boolean side_stitch,Boolean botton_zip,Long customer_id) {
        Long id = dbUtility.insertPant(pleat,belt_loop,back_pocket,waist,pocket_type,ticket_pocket,side_stitch,botton_zip,customer_id);
        Toast.makeText(getApplicationContext(), "Pant Inserted : "+Long.toString(id) , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
        Bundle b = new Bundle();
//        b.putString("name", name);
//        b.putString("email", email);
//        b.putString("phone", phone);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void insertShirt(String collor,String pocket,String hand_cuff,String shirt_style,String shoulder_style,String shirt_type,String mundah,Boolean patti,Boolean back_pleat,Boolean hand_fold,Long customer_id) {
        Long id = dbUtility.insertShirt(collor,pocket,hand_cuff,shirt_style,shoulder_style,shirt_type,mundah,patti,back_pleat,hand_fold,customer_id);
        Toast.makeText(getApplicationContext(), "Shirt Inserted : " + Long.toString(id), Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
        Bundle b = new Bundle();
//        b.putString("name", name);
//        b.putString("email", email);
//        b.putString("phone", phone);
        intent.putExtras(b);
        startActivity(intent);
    }


        @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, CustomerDetailActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle b = new Bundle();
                b.putLong("customer_id", Customer_Id);
                homeIntent.putExtras(b);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
