package com.paul.shelton.measureit.utils;

import android.widget.Adapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by ecom-shelton.paul on 23/07/17.
 */
public class SpinnerUtility {
    public static int getIndexofSpinnerOption(Spinner spin,String value)
    {
        Adapter adapter = spin.getAdapter();
        int n = adapter.getCount();
        ArrayList<String> users = new ArrayList<String>(n);
        for (int i = 0; i < n; i++) {
            if(adapter.getItem(i).equals(value))
                    return i;

        }
        return -1;

    }
}
