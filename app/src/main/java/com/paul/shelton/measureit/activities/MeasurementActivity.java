package com.paul.shelton.measureit.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import com.google.android.gms.common.api.GoogleApiClient;
import com.paul.shelton.measureit.listners.CustomOnItemSelectedListener;
import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.utils.DBUtility;
import com.paul.shelton.measureit.utils.SpinnerUtility;

import java.util.ArrayList;

    public class MeasurementActivity extends AppCompatActivity {
        private Spinner meausreType;
        private Button btnSubmit;
        private DBUtility dbUtility;
        private Long CustomerId;
        private Long MeasurementId;
        /**
         * ATTENTION: This was auto-generated to implement the App Indexing API.
         * See https://g.co/AppIndexing/AndroidStudio for more information.
         */
        private GoogleApiClient client;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_measurement);
            dbUtility = new DBUtility(this);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            meausreType = (Spinner) findViewById(R.id.measurement_type);

            Bundle b = getIntent().getExtras();
            CustomerId = b.getLong("customerId");
            MeasurementId = b.getLong("measurementId");
            Log.v("MeasurementId", MeasurementId + "");

            if (MeasurementId != 0) {
                String type = b.getString("type");
                if (type.equals("Shirt")) {
                    Cursor rs = dbUtility.getShirt(MeasurementId);
                    rs.moveToFirst();

                    String collor = rs.getString(1);
                    String pocket = rs.getString(2);
                    String hand_cuff = rs.getString(3);
                    String shirt_style = rs.getString(4);
                    String shoulder_type = rs.getString(5);
                    String shirt_type = rs.getString(6);
                    String mundah = rs.getString(7);
                    Boolean patti = rs.getInt(8) > 0;
                    Boolean back_pleat = rs.getInt(9) > 0;
                    Boolean hand_fold = rs.getInt(10) > 0;


                    ((EditText) findViewById(R.id.s_mundah)).setText(mundah);
                    ((EditText) findViewById(R.id.s_collor)).setText(collor);
                    ((EditText) findViewById(R.id.s_pocket)).setText(pocket);
                    ((EditText) findViewById(R.id.s_hand_cuff)).setText(hand_cuff);

                    ((AdapterView) findViewById(R.id.s_style_type)).setSelection(SpinnerUtility.getIndexofSpinnerOption((Spinner) findViewById(R.id.s_style_type), shirt_style));
                    ((AdapterView) findViewById(R.id.s_shoulder_type)).setSelection(SpinnerUtility.getIndexofSpinnerOption((Spinner) findViewById(R.id.s_shoulder_type), shoulder_type));
                    ((AdapterView) findViewById(R.id.s_shirt_type)).setSelection(SpinnerUtility.getIndexofSpinnerOption((Spinner) findViewById(R.id.s_shirt_type), shirt_type));

                    ((CheckBox) findViewById(R.id.s_patti)).setChecked(patti);
                    ((CheckBox) findViewById(R.id.s_back_pleat)).setChecked(back_pleat);
                    ((CheckBox) findViewById(R.id.s_hand_fold)).setChecked(hand_fold);


                    // Set only Shirt in the Top Spinner Option

                    ArrayList<String> list = new ArrayList<String>();
                    list.add("Shirt");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, list);
//                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    meausreType.setAdapter(dataAdapter);
                    meausreType.setSelection(0);
                } else {
                    Cursor rs = dbUtility.getPant(MeasurementId);
                    rs.moveToFirst();

                    String pleat = rs.getString(1);
                    String belt_loop = rs.getString(2);
                    String back_pocket = rs.getString(3);
                    String waist = rs.getString(4);
                    String pocket_type = rs.getString(5);
                    Boolean ticket_pocket = rs.getInt(6) > 0;
                    Boolean side_stitch = rs.getInt(7) > 0;
                    Boolean botton_zip = rs.getInt(8) > 0;

                    ((EditText) findViewById(R.id.p_pleat)).setText(pleat);
                    ((EditText) findViewById(R.id.p_beltloop)).setText(belt_loop);
                    ((EditText) findViewById(R.id.p_backpocket)).setText(back_pocket);
                    ((EditText) findViewById(R.id.p_waist)).setText(waist);

                    ((AdapterView) findViewById(R.id.p_pocket_type)).setSelection(SpinnerUtility.getIndexofSpinnerOption((Spinner) findViewById(R.id.p_pocket_type), pocket_type));

                    ((CheckBox) findViewById(R.id.p_ticket_pocket)).setChecked(ticket_pocket);
                    ((CheckBox) findViewById(R.id.p_side_stitch)).setChecked(side_stitch);
                    ((CheckBox) findViewById(R.id.p_bottom_zip)).setChecked(botton_zip);

                    // Set only Pant in the Top Spinner Option
                    ArrayList<String> list = new ArrayList<String>();
                    list.add("Pant");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, list);
                    meausreType.setAdapter(dataAdapter);
                    meausreType.setSelection(0);
                }
            }
            meausreType.setOnItemSelectedListener(new CustomOnItemSelectedListener());

            final Context cont = this;
            final Button create_pant = (Button) findViewById(R.id.create_pant);
            create_pant.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    insertUpdatePant();
                    gotoCustomerDetails();

                }
            });

            final Button create_shirt = (Button) findViewById(R.id.create_shirt_fs);
            create_shirt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    insertUpdateShirt();
                    gotoCustomerDetails();
                }
            });

            final Button add_shirt_to_order = (Button) findViewById(R.id.add_shirt_to_order);
            add_shirt_to_order.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MeasurementId = insertUpdateShirt();
                    String shirt_type = ((AdapterView) findViewById(R.id.s_shirt_type)).getSelectedItem().toString();
                    Long orderId = addOrderMeasurement(CustomerId,MeasurementId,"Shirt",shirt_type);
                    Log.v("orderId",orderId+"");
                    gotoOrderDetails(orderId);

            }
            });

            final Button add_pant_to_order = (Button) findViewById(R.id.add_pant_to_order);
            add_pant_to_order.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MeasurementId = insertUpdatePant();
                    Long orderId = addOrderMeasurement(CustomerId,MeasurementId,"Pant","Full");
                    Log.v("orderId",orderId+"");
                    gotoOrderDetails(orderId);
                }
            });

            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
//            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        }

        private Long addOrderMeasurement(Long customerId, Long measurementId,String type,String style) {
            Long orderId = dbUtility.getOrCreateOrder(customerId);
            Log.v("order-Id",orderId.toString());
            Log.v("measurementId",measurementId.toString());
            Log.v("type",type);
            Log.v("OrderMeasureMentExists ",Boolean.toString(dbUtility.orderMeasurementExists(orderId,measurementId,type)));
            if(!dbUtility.orderMeasurementExists(orderId,measurementId,type))
                dbUtility.addMeasurementtoOrder(orderId,measurementId,type,style);
            return orderId;
        }

        private Long insertUpdateShirt() {
            String collor = ((EditText) findViewById(R.id.s_collor)).getText().toString();
            String pocket = ((EditText) findViewById(R.id.s_pocket)).getText().toString();
            String hand_cuff = ((EditText) findViewById(R.id.s_hand_cuff)).getText().toString();
            String shirt_style = ((AdapterView) findViewById(R.id.s_style_type)).getSelectedItem().toString();
            String shoulder_style = ((AdapterView) findViewById(R.id.s_shoulder_type)).getSelectedItem().toString();
            String shirt_type = ((AdapterView) findViewById(R.id.s_shirt_type)).getSelectedItem().toString();
            String mundah = ((EditText) findViewById(R.id.s_mundah)).getText().toString();
            Boolean patti = ((CheckBox) findViewById(R.id.s_patti)).isChecked();
            Boolean back_pleat = ((CheckBox) findViewById(R.id.s_back_pleat)).isChecked();
            Boolean hand_fold = ((CheckBox) findViewById(R.id.s_hand_fold)).isChecked();

            if (MeasurementId == 0)
                MeasurementId = insertShirt(collor, pocket, hand_cuff, shirt_style, shoulder_style, shirt_type, mundah, patti, back_pleat, hand_fold, CustomerId);
            else
                updateShirt(MeasurementId, collor, pocket, hand_cuff, shirt_style, shoulder_style, shirt_type, mundah, patti, back_pleat, hand_fold, CustomerId);

            return MeasurementId;
        }

        private Long insertUpdatePant() {
            String pleat = ((EditText) findViewById(R.id.p_pleat)).getText().toString();
            String belt_loop = ((EditText) findViewById(R.id.p_beltloop)).getText().toString();
            String back_pocket = ((EditText) findViewById(R.id.p_backpocket)).getText().toString();
            String waist = ((EditText) findViewById(R.id.p_waist)).getText().toString();
            String pocket_type = ((AdapterView) findViewById(R.id.p_pocket_type)).getSelectedItem().toString();
            Boolean ticket_pocket = ((CheckBox) findViewById(R.id.p_ticket_pocket)).isChecked();
            Boolean side_stitch = ((CheckBox) findViewById(R.id.p_side_stitch)).isChecked();
            Boolean botton_zip = ((CheckBox) findViewById(R.id.p_bottom_zip)).isChecked();

            if (MeasurementId == 0)
                MeasurementId = insertPant(pleat, belt_loop, back_pocket, waist, pocket_type, ticket_pocket, side_stitch, botton_zip, CustomerId);
            else
                updatePant(MeasurementId, pleat, belt_loop, back_pocket, waist, pocket_type, ticket_pocket, side_stitch, botton_zip, CustomerId);
            return MeasurementId;
        }

        public Long insertPant(String pleat, String belt_loop, String back_pocket, String waist, String pocket_type, Boolean ticket_pocket, Boolean side_stitch, Boolean botton_zip, Long customer_id) {
            Long id = dbUtility.insertPant(pleat, belt_loop, back_pocket, waist, pocket_type, ticket_pocket, side_stitch, botton_zip, customer_id);
            Toast.makeText(getApplicationContext(), "Pant Inserted : " + Long.toString(id), Toast.LENGTH_SHORT).show();
            return id;
        }


        public Long updatePant(Long pantId, String pleat, String belt_loop, String back_pocket, String waist, String pocket_type, Boolean ticket_pocket, Boolean side_stitch, Boolean botton_zip, Long customer_id) {
            dbUtility.updatePant(pantId, pleat, belt_loop, back_pocket, waist, pocket_type, ticket_pocket, side_stitch, botton_zip, customer_id);
            Toast.makeText(getApplicationContext(), "Pant Updated : " + Long.toString(pantId), Toast.LENGTH_SHORT).show();
            return pantId;
        }

        public Long insertShirt(String collor, String pocket, String hand_cuff, String shirt_style, String shoulder_style, String shirt_type, String mundah, Boolean patti, Boolean back_pleat, Boolean hand_fold, Long customer_id) {
            Long id = dbUtility.insertShirt(collor, pocket, hand_cuff, shirt_style, shoulder_style, shirt_type, mundah, patti, back_pleat, hand_fold, customer_id);
            Toast.makeText(getApplicationContext(), "Shirt Inserted : " + Long.toString(id), Toast.LENGTH_SHORT).show();
            return id;
        }

        public Long updateShirt(Long shirtId, String collor, String pocket, String hand_cuff, String shirt_style, String shoulder_style, String shirt_type, String mundah, Boolean patti, Boolean back_pleat, Boolean hand_fold, Long customer_id) {
            dbUtility.updateShirt(shirtId, collor, pocket, hand_cuff, shirt_style, shoulder_style, shirt_type, mundah, patti, back_pleat, hand_fold, customer_id);
            Toast.makeText(getApplicationContext(), "Shirt Updated : " + Long.toString(shirtId), Toast.LENGTH_SHORT).show();
            return shirtId;
        }

        public void gotoCustomerDetails()
        {
            Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
            Bundle b = new Bundle();
            b.putLong("customer_id", this.CustomerId);
            intent.putExtras(b);
            startActivity(intent);
        }

        public void gotoOrderDetails(Long order_id)
        {
            Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
            Bundle b = new Bundle();
            b.putLong("order_id", order_id);
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
                    b.putLong("customer_id", this.CustomerId);
                    homeIntent.putExtras(b);
                    startActivity(homeIntent);
            }
            return (super.onOptionsItemSelected(menuItem));
        }

//        @Override
//        public void onStart() {
//            super.onStart();
//
//            // ATTENTION: This was auto-generated to implement the App Indexing API.
//            // See https://g.co/AppIndexing/AndroidStudio for more information.
//            client.connect();
//            Action viewAction = Action.newAction(
//                    Action.TYPE_VIEW, // TODO: choose an action type.
//                    "Measurement Page", // TODO: Define a title for the content shown.
//                    // TODO: If you have web page content that matches this app activity's content,
//                    // make sure this auto-generated web page URL is correct.
//                    // Otherwise, set the URL to null.
//                    Uri.parse("http://host/path"),
//                    // TODO: Make sure this auto-generated app URL is correct.
//                    Uri.parse("android-app://com.paul.shelton.measureit.activities/http/host/path")
//            );
////            AppIndex.AppIndexApi.start(client, viewAction);
//        }

//        @Override
//        public void onStop() {
//            super.onStop();
//
//            // ATTENTION: This was auto-generated to implement the App Indexing API.
//            // See https://g.co/AppIndexing/AndroidStudio for more information.
//            Action viewAction = Action.newAction(
//                    Action.TYPE_VIEW, // TODO: choose an action type.
//                    "Measurement Page", // TODO: Define a title for the content shown.
//                    // TODO: If you have web page content that matches this app activity's content,
//                    // make sure this auto-generated web page URL is correct.
//                    // Otherwise, set the URL to null.
//                    Uri.parse("http://host/path"),
//                    // TODO: Make sure this auto-generated app URL is correct.
//                    Uri.parse("android-app://com.paul.shelton.measureit.activities/http/host/path")
//            );
////            AppIndex.AppIndexApi.end(client, viewAction);
//            client.disconnect();
//        }
    }
