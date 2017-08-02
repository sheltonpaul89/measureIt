package com.paul.shelton.measureit.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paul.shelton.measureit.R;
import com.paul.shelton.measureit.classes.Customer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ecom-shelton.paul on 22/07/17.
 */
public class CustomerAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Customer> mDataSource;

    public CustomerAdapter(Context context, ArrayList<Customer> items) {
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
        View rowView = mInflater.inflate(R.layout.customer_list_view, viewGroup, false);

        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.customer_list_title);

        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.customer_list_subtitle);

        TextView detailTextView =
                (TextView) rowView.findViewById(R.id.customer_list_detail);

        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(R.id.customer_list_thumbnail);


        Customer customer = (Customer) getItem(i);

        titleTextView.setText(customer.name);
        subtitleTextView.setText(customer.email);
        detailTextView.setText(customer.phone);

        Picasso.with(mContext).load("http://www.freeiconspng.com/uploads/no-image-icon-33.png").placeholder(R.drawable.no_image_icon).into(thumbnailImageView);

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
