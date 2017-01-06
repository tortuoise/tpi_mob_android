package com.biz.stratadigm.tpi.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.biz.stratadigm.tpi.App;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.ui.view.BaseView;

import nucleus.factory.PresenterFactory;
import nucleus.presenter.Presenter;
import nucleus.view.PresenterLifecycleDelegate;

public abstract class BaseFragment<P extends Presenter> extends Fragment implements BaseView {
    private static final String PRESENTER_STATE_KEY = "presenter_state";

    private PresenterLifecycleDelegate<P> presenterDelegate;
    private final Context applicationContext;

    {
        applicationContext = App.getAppComponent().context();
    }

    public abstract PresenterFactory<P> getPresenterFactory();

    protected final P getPresenter() {
        return presenterDelegate.getPresenter();
    }

    protected final Context getApplicationContext() {
        return applicationContext;
    }

    @CallSuper
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenterDelegate = new PresenterLifecycleDelegate<>(getPresenterFactory());
        if (bundle != null) {
            presenterDelegate.onRestoreInstanceState(bundle.getBundle(PRESENTER_STATE_KEY));
        }
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState());
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        presenterDelegate.onResume(this);
    }

    @Override
    public final void showNetworkError() {
        Toast.makeText(applicationContext, R.string.no_internet_connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnexpectedError() {
        Toast.makeText(applicationContext, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
    }

    @CallSuper
    @Override
    public void onStop() {
        presenterDelegate.onDropView();
        super.onStop();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        presenterDelegate.onDestroy(!getActivity().isChangingConfigurations());
        super.onDestroy();
    }
}
