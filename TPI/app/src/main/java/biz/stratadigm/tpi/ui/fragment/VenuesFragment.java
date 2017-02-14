package biz.stratadigm.tpi.ui.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.support.v7.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ImageView;
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
import biz.stratadigm.tpi.ui.activity.StartActivity;
import biz.stratadigm.tpi.ui.adapter.VenueAdapter;
import biz.stratadigm.tpi.ui.view.VenuesView;
import biz.stratadigm.tpi.di.module.VenuesModule;
import biz.stratadigm.tpi.presenter.VenuesPresenter;
import biz.stratadigm.tpi.presenter.MenuPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.PresenterFactory;

//public class VenuesFragment extends Fragment {
public class VenuesFragment extends BaseFragment<VenuesPresenter> implements VenuesView {

    private static final String TAG = "TPI";

    @BindView(R.id.venueList)
    RecyclerView mList;

    @BindView(R.id.less)
    ImageView less;

    @BindView(R.id.more)
    ImageView more;

    //@Inject
    //MenuPresenter menuPresenter;

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
        setHasOptionsMenu(true);
        
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                try {
                showFilteringPopUpMenu();
                } catch (Exception e) {
                    Log.v(TAG, e.toString());   
                }
                break;
            case R.id.action_settings:
                break;
            case R.id.action_logout:
                try {
                        //menuPresenter.logout();
                        venuesPresenter.logout();
                } catch (Exception e) {
                        Log.v(TAG, e.toString());
                        //showStartScreen();
                }
                break;
         }
         return true;
    }
  
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_menu_main, menu);
    }

    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getApplicationContext(), getActivity().findViewById(R.id.menu_filter));
        try {
        popup.getMenuInflater().inflate(R.menu.filter_items, popup.getMenu());
        } catch (Exception e) {
             Log.v(TAG, e.toString());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mine:
                        //mPresenter.setFiltering(TasksFilterType.ACTIVE_TASKS);
                        break;
                    case R.id.nearby:
                        //mPresenter.setFiltering(TasksFilterType.COMPLETED_TASKS);
                        break;
                    default:
                        //mPresenter.setFiltering(TasksFilterType.ALL_TASKS);
                        break;
                }
                //mPresenter.loadTasks(false);
                return true;
            }
        });

        popup.show();
    }

    @Override
    public void showStartScreen() {
        Log.v(TAG, "Finished");
        Intent intent = StartActivity.getStartIntent(getActivity());
        startActivity(intent);
        //startActivity(new Intent(getApplicationContext(), StartActivity.class));
        getActivity().finish();
        //startActivity(new Intent(getApplicationContext(), StartActivity.class));
        //finish();
    }

}
