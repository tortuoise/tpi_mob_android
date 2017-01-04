package com.biz.stratadigm.tpi.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.biz.stratadigm.tpi.entity.dto.VenueDTO;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.ui.activity.MainActivity;
import com.biz.stratadigm.tpi.ui.activity.VenueListTahli;
import com.biz.stratadigm.tpi.ui.fragment.ThaliFragment;
import com.biz.stratadigm.tpi.tools.Constant;

import java.util.ArrayList;

/**
 * Created by tamara on 12/19/16.
 */

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.Holder> {
    private ArrayList<VenueDTO> mDataset = new ArrayList<>();
    private Context mContext;
    private SharedPreferences sharedPreferences;


    public class Holder extends RecyclerView.ViewHolder {
        public TextView id, name, lat, lng, submitted, thalis, venueList;
        public LinearLayout mRoot;

        public Holder(View view) {
            super(view);
            mRoot = (LinearLayout) view.findViewById(R.id.root);
            id = (TextView) view.findViewById(R.id.id);
            id.setVisibility(View.GONE);
            name = (TextView) view.findViewById(R.id.name);
            submitted = (TextView) view.findViewById(R.id.submitted);
            submitted.setVisibility(View.GONE);
            lng = (TextView) view.findViewById(R.id.lng);
            lat = (TextView) view.findViewById(R.id.lat);
            thalis = (TextView) view.findViewById(R.id.thalis);
            venueList = (TextView) view.findViewById(R.id.list);
            venueList.setTextColor(mContext.getResources().getColor(R.color.blue));
        }
    }


    public VenueAdapter(ArrayList<VenueDTO> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(Constant.TAG, Context.MODE_PRIVATE);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Holder onCreateViewHolder(ViewGroup parent,
                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.venue_item, parent, false);

        Holder vh = new Holder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        // animate(holder);
        final VenueDTO venue = mDataset.get(position);
        holder.id.setText("ID: " + venue.id);
        holder.name.setText("Name: " + venue.name);
        Log.e("name", venue.name);
        holder.submitted.setText("Submitted: " + venue.submitted);
        Log.e("name", venue.submitted);
        holder.lat.setText("Lat: " + venue.lat);
        holder.lng.setText("Lng: " + venue.lnh);
        holder.thalis.setText("Thalis: " + venue.thalis);

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("venue", venue.id);
                ThaliFragment.venue.setText(venue.id);
                editor.apply();
                MainActivity.mViewPagerJob.setCurrentItem(2);
            }
        });
        holder.venueList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext.getApplicationContext(), VenueListTahli.class);
                i.putExtra("id", position);
                mContext.startActivity(i);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataset.size();
    }

}



