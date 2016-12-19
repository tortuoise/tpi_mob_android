package com.biz.stratadigm.tpi.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.biz.stratadigm.tpi.DataVenue;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.components.CustomTextView;

import java.util.ArrayList;

/**
 * Created by tamara on 12/19/16.
 */

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.Holder> {
    private ArrayList<DataVenue> mDataset = new ArrayList<>();
    private Context mContext;
    private String citanjenivo,pisanjenivo,konverzacijanivo;




    public class Holder extends RecyclerView.ViewHolder {
        public CustomTextView id,name,lat,lng,submitted,thalis;

        public Holder(View view) {
            super(view);

            id = (CustomTextView) view.findViewById(R.id.id);
            name = (CustomTextView) view.findViewById(R.id.name);
            submitted = (CustomTextView) view.findViewById(R.id.submitted);
            lng = (CustomTextView) view.findViewById(R.id.lng);
            lat = (CustomTextView) view.findViewById(R.id.lat);
            thalis = (CustomTextView) view.findViewById(R.id.thalis);
        }
    }


    public VenueAdapter(ArrayList<DataVenue> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Holder onCreateViewHolder(ViewGroup parent,
                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thali_item, parent, false);

        Holder vh = new Holder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        // animate(holder);
        final DataVenue venue = mDataset.get(position);
        holder.id.setText("ID: " +venue.id);
        holder.name.setText("Name: "+venue.name);
        holder.submitted.setText("Submitted: "+venue.submitted);
        holder.lat.setText("Lat: "+venue.lat);
        holder.lng.setText("Lng: "+venue.lnh);
        holder.thalis.setText("Lng: "+venue.thalis);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataset.size();
    }

}



