package biz.stratadigm.tpi.ui.adapter;

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

import java.util.ArrayList;
import java.util.List;

import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.entity.dto.VenueDTO;
import biz.stratadigm.tpi.entity.vo.VenueVO;
import biz.stratadigm.tpi.tools.Constant;
import biz.stratadigm.tpi.ui.activity.BrowseActivity;
import biz.stratadigm.tpi.ui.activity.VenueListThali;
import biz.stratadigm.tpi.ui.adapter.viewholder.BaseViewHolder;
import biz.stratadigm.tpi.ui.fragment.ThaliFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VenueAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "TPI";
    private ArrayList<VenueVO> venues = new ArrayList<>();
    private Context mContext;
    private SharedPreferences sharedPreferences;


    /*public class Holder extends RecyclerView.ViewHolder {
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
    }*/


    /*public VenueAdapter(ArrayList<VenueDTO> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(Constant.TAG, Context.MODE_PRIVATE);
    }*/

    // Create new views (invoked by the layout manager)
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent,
                                     int viewType) {
        // create a new view
        /*View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venue, parent, false);

        Holder vh = new Holder(v);
        return vh;
        */
        Log.v(TAG, "VenueAdapter:onCreateViewHolder");
        return new VenueViewHolder(parent);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
    //public void onBindViewHolder(final Holder holder, final int position) {
        Log.v(TAG, "VenueAdapter:onBindViewHolder " + venues.size());
        if (holder == null) return;
        try {
        if (holder instanceof VenueViewHolder) {
            ((VenueViewHolder) holder).bind(venues.get(position));
        }
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }
                // animate(holder);
                /*final VenueDTO venue = mDataset.get(position);
                holder.id.setText("ID: " + venue.getId());
                holder.name.setText("Name: " + venue.getName());
                Log.v(TAG, venue.getName());
                holder.submitted.setText("Submitted: " + venue.getSubmitted());
                Log.v(TAG, venue.getSubmitted());
                holder.lat.setText("Lat: " + Float.toString(venue.getLocation().getLat()));
                holder.lng.setText("Lng: " + Float.toString(venue.getLocation().getLng()));
                holder.thalis.setText("Thalis: " + venue.getThalis());

                holder.mRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("venue", Long.toString(venue.getId()));
                        ThaliFragment.venue.setText(Long.toString(venue.getId()));
                        editor.apply();
                        BrowseActivity.jobViewPager.setCurrentItem(2);
                    }
                });
                holder.venueList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(mContext.getApplicationContext(), VenueListThali.class);
                        i.putExtra("id", position);
                        mContext.startActivity(i);
                    }
                });*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return venues.size();
    }

    public void addVenues(List<VenueVO> newVenues) {
        this.venues.addAll(newVenues);
    }

    public void setVenues(List<VenueVO> newVenues) {
        Log.v(TAG, "VenueAdapter: setVenues" + newVenues.size());
        this.venues = new ArrayList<>(newVenues);
        notifyDataSetChanged();
    }

    static class VenueViewHolder extends BaseViewHolder {

        @BindView(R.id.name)
        TextView nameTextView;

        @BindView(R.id.lat)
        TextView latTextView;

        @BindView(R.id.lng)
        TextView lngTextView;


        public VenueViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_venue);
            ButterKnife.bind(this, itemView);
        }

        public void bind(VenueVO venue) {
            Log.v(TAG, "ViewHolder " + venue.getName());
            try {
                 nameTextView.setText(venue.getName());
                 latTextView.setText(venue.getLocation().getLat().toString());
                 lngTextView.setText(venue.getLocation().getLng().toString());
            } catch (Exception e) {
                 Log.v(TAG, "ViewHolder " + e.toString());
                
            }
        }
    }
}

