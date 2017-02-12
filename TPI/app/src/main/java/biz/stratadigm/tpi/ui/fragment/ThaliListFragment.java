package biz.stratadigm.tpi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.PresenterFactory;

public class ThaliListFragment extends BaseFragment<ThaliListPresenter> implements ThaliListView {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
    public void showThalis(List<ThaliVO> newThalis) {
        thaliAdapter.showThalis(newThalis);
    }

    @Override
    public void addThalis(List<ThaliVO> newThalis) {
        thaliAdapter.addThalis(newThalis);
    }

    @Override
    public void showAuthError() {
        Toast.makeText(getApplicationContext(), "Invalid login or password", Toast.LENGTH_SHORT).show();
    }
}
