package com.biz.stratadigm.tpi.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.biz.stratadigm.tpi.DataThali;
import com.biz.stratadigm.tpi.DataVenue;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.components.CustomTextView;
import com.biz.stratadigm.tpi.tools.Constant;

import java.util.ArrayList;

/**
 * Created by tamara on 12/19/16.
 */

public class ThaliAdapter extends RecyclerView.Adapter<ThaliAdapter.Holder> {
    private ArrayList<DataThali> mDataset = new ArrayList<>();
    private Context mContext;
    private SharedPreferences sharedPreferences;


    public class Holder extends RecyclerView.ViewHolder {
        public CustomTextView id,name,target,limited,region,price,image,userid,venue,verified,accepted,submitted;
        public LinearLayout root;

        public Holder(View view) {
            super(view);

            id = (CustomTextView) view.findViewById(R.id.id);
            name = (CustomTextView) view.findViewById(R.id.name);
            submitted = (CustomTextView) view.findViewById(R.id.submitted);
            target = (CustomTextView) view.findViewById(R.id.target);
            limited = (CustomTextView) view.findViewById(R.id.limited);
            region = (CustomTextView) view.findViewById(R.id.region);
            price = (CustomTextView) view.findViewById(R.id.price);
            userid = (CustomTextView) view.findViewById(R.id.userid);
            venue = (CustomTextView) view.findViewById(R.id.venue);
            verified = (CustomTextView) view.findViewById(R.id.verified);
            accepted = (CustomTextView) view.findViewById(R.id.accepeted);
            root = (LinearLayout) view.findViewById(R.id.root);
        }
    }


    public ThaliAdapter(ArrayList<DataThali> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(Constant.TAG, Context.MODE_PRIVATE);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ThaliAdapter.Holder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thali_item, parent, false);

        ThaliAdapter.Holder vh = new ThaliAdapter.Holder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ThaliAdapter.Holder holder, final int position) {
        // animate(holder);
        final DataThali thali = mDataset.get(position);
        holder.id.setText("ID: " +thali.id);
        holder.name.setText("Name: "+thali.name);
        Log.e("name",thali.name);
        holder.submitted.setText("Submitted: "+thali.submitted);Log.e("name",thali.submitted);
        holder.accepted.setText("Accepted: "+thali.accepted);
        holder.verified.setText("Verified: "+thali.verified);
        holder.venue.setText("Venue: "+thali.venue);
        holder.userid.setText("UserID: "+thali.userid);
        holder.limited.setText("Limited: "+thali.limited);
        holder.price.setText("Price: "+thali.price);
        holder.region.setText("Region: "+thali.region);
        holder.target.setText("Target: "+thali.target);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("thali",thali.id);
                editor.apply();
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataset.size();
    }
}



