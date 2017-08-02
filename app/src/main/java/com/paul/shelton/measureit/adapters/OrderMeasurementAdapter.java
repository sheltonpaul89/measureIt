package com.paul.shelton.measureit.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.classes.Measurement;
import com.paul.shelton.measureit.classes.OrderMeasurement;

import java.util.ArrayList;

/**
 * Created by ecom-shelton.paul on 22/07/17.
 */
public class OrderMeasurementAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<OrderMeasurement> mDataSource;

    public OrderMeasurementAdapter(Context context, ArrayList<OrderMeasurement> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.order_measurement_list_view, viewGroup, false);

        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.order_measurement_list_title);

        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.order_measurement_list_subtitle);

        TextView detailTextView =
                (TextView) rowView.findViewById(R.id.order_measurement_list_detail);

        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(R.id.order_measurement_list_thumbnail);

//        EditText quantityText =
//                (EditText) rowView.findViewById(R.id.order_measurement_list_quantity);
        EditText quantityText = ((EditText) rowView.findViewById(R.id.order_measurement_list_quantity));

        OrderMeasurement om = (OrderMeasurement)getItem(i);
        Measurement measurement = om.measuremenet;

        Log.v("om.Quantity",om.Quantity+"");
        quantityText.setText(om.Quantity+"");
        titleTextView.setText(measurement.type);
        subtitleTextView.setText(measurement.style);
        detailTextView.setText(measurement.displayDate);

        switch (measurement.type)
        {
            case "Pant":
                thumbnailImageView.setBackgroundResource(R.drawable.pant);
//                thumbnailImageView.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.pant));

                break;
            case "Shirt":
                switch (measurement.style)
                {
                    case "Half Hand":
                        thumbnailImageView.setBackgroundResource(R.drawable.hs);
                        break;
                    case "Full Hand":
                        thumbnailImageView.setBackgroundResource(R.drawable.fs);
                        break;
                }
        }


        Typeface titleTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/JosefinSans-Bold.ttf");
        titleTextView.setTypeface(titleTypeFace);

        Typeface subtitleTypeFace =
                Typeface.createFromAsset(mContext.getAssets(), "fonts/JosefinSans-SemiBoldItalic.ttf");
        subtitleTextView.setTypeface(subtitleTypeFace);

        Typeface detailTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/Quicksand-Bold.otf");
        detailTextView.setTypeface(detailTypeFace);

        return rowView;
    }
}
