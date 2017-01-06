package com.biz.stratadigm.tpi.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.biz.stratadigm.tpi.App;
import com.biz.stratadigm.tpi.BuildConfig;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.di.component.DaggerLoginComponent;
import com.biz.stratadigm.tpi.di.module.LoginModule;
import com.biz.stratadigm.tpi.presenter.LoginPresenter;
import com.biz.stratadigm.tpi.ui.activity.MainActivity;
import com.biz.stratadigm.tpi.ui.view.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.PresenterFactory;

/**
 * Created by tamara on 12/22/16.
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView {

    @BindView(R.id.editTextEmail)
    EditText emailEditText;

    @BindView(R.id.editTextPass)
    EditText passwordEditText;

    @BindView(R.id.buttonLogin)
    Button mConfirm;

    @Inject
    LoginPresenter loginPresenter;

    {
        DaggerLoginComponent.builder()
                .appComponent(App.getAppComponent())
                .loginModule(new LoginModule())
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            setUpDefaultFields();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpDefaultFields() {
        if (BuildConfig.DEBUG) {
            emailEditText.setText("dec@ember.com");
            passwordEditText.setText("allurbase");
        }
    }

    @Override
    public PresenterFactory<LoginPresenter> getPresenterFactory() {
        return () -> loginPresenter;
    }

    @OnClick(R.id.buttonLogin)
    void onLoginButtonClicked() {
        getPresenter().onLoginButtonClicked();
    }

    @Override
    public String getUsername() {
        return emailEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEditText.getText().toString();
    }

    @Override
    public void showAuthError() {
        Toast.makeText(getApplicationContext(), "Invalid login or password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainScreen() {
        Intent intent = MainActivity.getStartIntent(getActivity());
        startActivity(intent);
        getActivity().finish();
    }
}
