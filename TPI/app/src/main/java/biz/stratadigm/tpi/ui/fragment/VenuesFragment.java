package biz.stratadigm.tpi.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

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
import biz.stratadigm.tpi.ui.activity.AddEditVenueActivity;
import biz.stratadigm.tpi.ui.adapter.VenueAdapter;
import biz.stratadigm.tpi.ui.view.VenuesView;
import biz.stratadigm.tpi.di.module.VenuesModule;
import biz.stratadigm.tpi.presenter.VenuesPresenter;
import biz.stratadigm.tpi.presenter.VenuesFilterType;
import biz.stratadigm.tpi.presenter.MenuPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.PresenterFactory;

public class VenuesFragment extends BaseFragment<VenuesPresenter> implements VenuesView {

    private static final String TAG = "TPI";
    public final static String ARG_OFFSET = "offset";
    private boolean refreshed = true;

    @BindView(R.id.venueList)
    RecyclerView mList;

    @BindView(R.id.less)
    ImageView less;

    @BindView(R.id.venuesFilterLabel)
    TextView venuesFilterLabelView;

    @BindView(R.id.more)
    ImageView more;

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
    private VenueAdapter mVenueAdapter;
    private SharedPreferences sharedPreferences;
    private int offset = 0;

    OnVenueSelectedListener venueSelect;

    //Container activity must implement this interface so fragment can deliver messages.
    public interface OnVenueSelectedListener {
        public void onVenueSelect(long position, String name);
        public void onVenueLongSelect(int position, String name);
    }

    @Override 
    public void onAttach(Context activity) {
        super.onAttach(activity);
        refreshed = true;
        Bundle args = getArguments();
        if (args != null) {
            offset = args.getInt(ARG_OFFSET);
            venuesPresenter.setOffset(offset);
        } 
        else  
            venuesPresenter.setOffset(offset); 
        try {
            venueSelect = (OnVenueSelectedListener) activity;
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            throw new ClassCastException(activity.toString() + "must implement OnVenueSelectedListener");
        }
    }

    @Override
    public PresenterFactory<VenuesPresenter> getPresenterFactory() {
        return () -> venuesPresenter;
    }

    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venues, container, false);

        setHasOptionsMenu(true);

        if (!refreshed) {
            venuesPresenter.refresh(); 
            refreshed = true;
        }

        ButterKnife.bind(this, view);

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
 
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //mPresenter.addNewVenue();
                 showAddVenue();
             }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "VenuesFragment.onViewCreated");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mVenueAdapter = new VenueAdapter();
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(mVenueAdapter);
        mList.addOnItemTouchListener(
            new RecyclerItemClickListener(view.getContext(), mList,new RecyclerItemClickListener.OnItemClickListener() {
                @Override public void onItemClick(View view, int position) {
                    try {
                        venueSelect.onVenueSelect(venuesPresenter.getCache().get(position).getId(), venuesPresenter.getCache().get(position).getName());
                    } catch (Exception e) {
                        Log.v(TAG, e.toString());
                    }
                    //Toast.makeText(getApplicationContext(), "Clicked" + position, Toast.LENGTH_LONG).show();
                }
                @Override public void onLongItemClick(View view, int position) {
                    venueSelect.onVenueLongSelect(venuesPresenter.getCache().get(position).getId().intValue(), venuesPresenter.getCache().get(position).getName());   
                }
            })
        );
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset = venuesPresenter.getOffset() + 20;
                venuesPresenter.setOffset(offset);
                venuesPresenter.refresh();
                mVenueAdapter.notifyDataSetChanged();
            }
        });
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset = venuesPresenter.getOffset() - 20;
                offset = offset <= 0 ? 0 : offset;
                venuesPresenter.setOffset(offset);
                venuesPresenter.refresh();
                mVenueAdapter.notifyDataSetChanged();
            }
        });
        refreshed = false;
        super.onViewCreated(view, savedInstanceState);
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
    public void showAddVenue() {
        Intent intent = new Intent(getContext(), AddEditVenueActivity.class);
        startActivityForResult(intent, AddEditVenueActivity.REQUEST_ADD_VENUE);
    }


    @Override
    public void showAuthError() {
        Toast.makeText(getApplicationContext(), "Invalid login or password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMineFilterLabel() {
        venuesFilterLabelView.setText(getResources().getString(R.string.filter_mine));
    }
  
    @Override
    public void showNearbyFilterLabel() {
        venuesFilterLabelView.setText(getResources().getString(R.string.filter_nearby));
    }
  
    @Override
    public void showAllFilterLabel() {
        venuesFilterLabelView.setText(getResources().getString(R.string.filter_all));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                showFilteringPopUpMenu();
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
        inflater.inflate(R.menu.venues, menu);
    }

    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getApplicationContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.items_filter, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mine:
                        venuesPresenter.setFiltering(VenuesFilterType.MINE_VENUES);
                        venuesPresenter.refresh(); 
                        break;
                    case R.id.nearby:
                        venuesPresenter.setFiltering(VenuesFilterType.NEARBY_VENUES);
                        venuesPresenter.refresh(); 
                        break;
                    default:
                        venuesPresenter.setFiltering(VenuesFilterType.ALL_VENUES);
                        venuesPresenter.refresh(); 
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
