package com.paul.shelton.measureit.Listners;

import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.paul.shelton.measureit.R;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();

        Log.v("*********************","The View Value");
        Log.i("ViewName", view.getParent().getParent().getClass().getName());

        RelativeLayout measurementFieldLayout = (RelativeLayout)((LinearLayout)parent.getParent().getParent()).findViewById(R.id.measurement_container);
        final int childCount = measurementFieldLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = measurementFieldLayout.getChildAt(i);
            Log.i("Child View ", v.getClass().getName());
        }

        LinearLayout includePant = (LinearLayout) measurementFieldLayout.findViewById(R.id.include_pant);
        View includeShirtFs = measurementFieldLayout.findViewById(R.id.include_shirt_fs);
        View includeShirtHs = measurementFieldLayout.findViewById(R.id.include_shirt_hs);

        includePant.setVisibility(View.INVISIBLE);
        includeShirtFs.setVisibility(View.INVISIBLE);
        includeShirtHs.setVisibility(View.INVISIBLE);


        switch (parent.getItemAtPosition(pos).toString())
        {
            case "Pant":
                includePant.setVisibility(View.VISIBLE);
                break;
            case "Shirt":
                includeShirtFs.setVisibility(View.VISIBLE);
                break;
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}