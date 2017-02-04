package biz.stratadigm.tpi.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import java.util.List;
import java.util.ArrayList;

import biz.stratadigm.tpi.App;
import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.di.component.DaggerVenuesComponent;
import biz.stratadigm.tpi.entity.dto.VenueDTO;
import biz.stratadigm.tpi.entity.vo.VenueVO;
import biz.stratadigm.tpi.tools.Constant;
import biz.stratadigm.tpi.ui.adapter.VenueAdapter;
import biz.stratadigm.tpi.ui.view.VenuesView;
import biz.stratadigm.tpi.di.module.VenuesModule;
import biz.stratadigm.tpi.presenter.VenuesPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.PresenterFactory;

//public class VenuesFragment extends Fragment {
public class VenuesFragment extends BaseFragment<VenuesPresenter> implements VenuesView {

    private static final String TAG = "TPI";

    @BindView(R.id.venueList)
    RecyclerView mList;

    @Inject
    VenuesPresenter venuesPresenter;
    {
        DaggerVenuesComponent.builder()
                .appComponent(App.getAppComponent())
                .venuesModule(new VenuesModule())
                .build()
                .inject(this);
    }
   
    private RecyclerView.LayoutManager mLayoutMAnager;
    //private ArrayList<VenueDTO> mListVenue = new ArrayList<>();
    private VenueAdapter mVenueAdapter;
    private SharedPreferences sharedPreferences;
    private int offset = 0;
    private TextView more, less;

    //public static VenuesFragment newInstance() {
    //    return new VenuesFragment();
    //}

    @Override
    public PresenterFactory<VenuesPresenter> getPresenterFactory() {
        return () -> venuesPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venues, container, false);

        Log.v(TAG, "VenuesFragment: onCreateView");
                /*
                sharedPreferences = getActivity().getSharedPreferences(Constant.TAG, Context.MODE_PRIVATE);
                //mList = (RecyclerView) view.findViewById(R.id.venueList);
                //mVenueAdapter = new VenueAdapter(mListVenue, getActivity());
                mLayoutMAnager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                mList.setLayoutManager(mLayoutMAnager);
                mList.setAdapter(mVenueAdapter);
                getVenueList();
                
                more = (TextView) view.findViewById(R.id.more);
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        offset = offset + 20;
                        mListVenue.clear();
                        getVenueList();
                        mVenueAdapter.notifyDataSetChanged();
                    }
                });
                less = (TextView) view.findViewById(R.id.less);
                less.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (offset >= 20) {
                            offset = offset - 20;
                            mListVenue.clear();
                            getVenueList();
                            mVenueAdapter.notifyDataSetChanged();
                        }
                    }
                });
                */
        
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "VenuesFragment: onViewCreated");
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mVenueAdapter = new VenueAdapter();
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(mVenueAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    private void getVenueList() {
        /*Log.v(TAG, "getVenueList");
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.VENUESLIST + "?offset=" + offset,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray response1 = new JSONArray(response);
                            for (int i = 0; i < response1.length(); i++) {
                                JSONObject ith = response1.getJSONObject(i);
                                Long id = ith.getLong("id");
                                String name = ith.getString("name");
                                String submitted = ith.getString("submitted");
                                JSONObject location = ith.getJSONObject("location");
                                Float lat = new Float(location.getDouble("Lat"));
                                Float lng = new Float(location.getDouble("Lng"));
                                ArrayList<Long> ithalis = new ArrayList<Long>();
                                if (!ith.isNull("thalis")) {
                                    JSONArray thalis = ith.getJSONArray("thalis");
                                    for (int j = 0; j < thalis.length(); j++)
                                        ithalis.add(new Long(thalis.getInt(j)));
                                }
                                VenueDTO venue = new VenueDTO(id, name, submitted, lat, lng, ithalis);
                                mListVenue.add(venue);
                                mVenueAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Log.v(TAG, e.toString() );
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);//post request on queue
        */
    }

    @Override
    public void showVenues(List<VenueVO> newVenues) {
        Log.v(TAG, "showVenues: "+newVenues.size());
        mVenueAdapter.setVenues(newVenues);
    }

    @Override
    public void addVenues(List<VenueVO> newVenues) {
        mVenueAdapter.addVenues(newVenues);
    }

    @Override
    public void showAuthError() {
        Toast.makeText(getApplicationContext(), "Invalid login or password", Toast.LENGTH_SHORT).show();
    }
}
