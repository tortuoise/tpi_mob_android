package biz.stratadigm.tpi.ui.fragment;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import biz.stratadigm.tpi.App;
import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.di.component.DaggerThaliListComponent;
import biz.stratadigm.tpi.entity.vo.ThaliVO;
import biz.stratadigm.tpi.presenter.ThaliListPresenter;
import biz.stratadigm.tpi.ui.adapter.ThaliAdapter;
import biz.stratadigm.tpi.di.module.ThaliListModule;
import biz.stratadigm.tpi.ui.view.ThaliListView;
import biz.stratadigm.tpi.ui.activity.AddEditThaliActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.PresenterFactory;

public class ThaliListFragment extends BaseFragment<ThaliListPresenter> implements ThaliListView {

    private static final String TAG = "TPI";
    public final static String ARG_POSITION = "position";
    public final static String ARG_NAME = "name";
    //int mCurrentPosition = -1;

    @BindView(R.id.thalisFilterLabel)
    TextView thalisFilterLabelView;

    @BindView(R.id.thalis)
    RecyclerView thaliRecyclerView;

    @Inject
    ThaliListPresenter thaliListPresenter;
    {
        DaggerThaliListComponent.builder()
                .appComponent(App.getAppComponent())
                .thaliListModule(new ThaliListModule())
                .build()
                .inject(this);
    }

    private ThaliAdapter thaliAdapter;

    public static ThaliListFragment newInstance() {
        return new ThaliListFragment();
    }

    //@Override 
    //public void onCreate(Bundle savedInstanceState) {
        //if (savedInstanceState != null) {
        //    thaliListPresenter.setCurrentPosition(savedInstanceState.getInt(ARG_POSITION));
        //}
    //}

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        Bundle args = getArguments();
        if (args != null) {
            thaliListPresenter.setCurrentPosition(args.getInt(ARG_POSITION)); 
            thaliListPresenter.setCurrentVenueName(args.getString(ARG_NAME)); 
        } else if (thaliListPresenter.getCurrentPosition() != -1) {
            thaliListPresenter.setCurrentPosition(0); 
            thaliListPresenter.setCurrentVenueName(getResources().getString(R.string.unknown)); 
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            //mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            thaliListPresenter.setCurrentPosition(savedInstanceState.getInt(ARG_POSITION));
            thaliListPresenter.setCurrentVenueName(savedInstanceState.getString(ARG_NAME));
        }
        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
 
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //mPresenter.addNewVenue();
                 showAddThali();
             }
        });
        return inflater.inflate(R.layout.fragment_thali_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        thaliAdapter = new ThaliAdapter();
        thaliRecyclerView.setLayoutManager(linearLayoutManager);
        thaliRecyclerView.setAdapter(thaliAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, thaliListPresenter.getCurrentPosition() /*mCurrentPosition*/ );
        outState.putString(ARG_NAME, thaliListPresenter.getCurrentVenueName() /*mCurrentPosition*/ );
    }

    @Override
    public PresenterFactory getPresenterFactory() {
        return () -> thaliListPresenter;
    }

    @Override
    public void showSplashLoader(boolean show) {
        thaliAdapter.showSplashLoader(true);
    }

    @Override
    public void setThalis(List<ThaliVO> newThalis) {
        thaliAdapter.setThalis(newThalis);
    }

    @Override
    public void showThalisFilterLabel(String venueName) {
        thalisFilterLabelView.setText(getResources().getString(R.string.filter_thalis) + " " + venueName);
    }

    @Override
    public void showThalis(List<ThaliVO> newThalis) {
        thaliAdapter.showThalis(newThalis);
    }

    @Override
    public void addThalis(List<ThaliVO> newThalis) {
        thaliAdapter.addThalis(newThalis);
    }

    @Override
    public void showAddThali() {
        Intent intent = new Intent(getContext(), AddEditThaliActivity.class);
        startActivityForResult(intent, AddEditThaliActivity.REQUEST_ADD_THALI);
    }

    @Override
    public void showAuthError() {
        Toast.makeText(getApplicationContext(), "Invalid login or password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.thalis, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_back:
                getActivity().onBackPressed();
                break;
         }
         return true;
    }
}
