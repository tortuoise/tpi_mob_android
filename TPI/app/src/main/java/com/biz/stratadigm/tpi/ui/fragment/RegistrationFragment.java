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
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.di.component.DaggerRegistrationComponent;
import com.biz.stratadigm.tpi.di.module.RegistrationModule;
import com.biz.stratadigm.tpi.presenter.RegistrationPresenter;
import com.biz.stratadigm.tpi.ui.activity.MainActivity;
import com.biz.stratadigm.tpi.ui.view.RegistrationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.PresenterFactory;

/**
 * Created by tamara on 12/11/16.
 */

public class RegistrationFragment extends BaseFragment<RegistrationPresenter> implements RegistrationView {

    @BindView(R.id.editTextEmail)
    EditText emailEditText;

    @BindView(R.id.editTextName)
    EditText nameEditText;

    @BindView(R.id.editTextPass)
    EditText passwordEditText;

    @BindView(R.id.buttonLogin)
    Button registerButton;

    @Inject
    RegistrationPresenter registrationPresenter;

    {
        DaggerRegistrationComponent.builder()
                .appComponent(App.getAppComponent())
                .registrationModule(new RegistrationModule())
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.buttonLogin)
    void onRegisterButtonClicked() {
        getPresenter().onRegisterButtonClicked();
    }

    @Override
    public String getEmail() {
        return emailEditText.getText().toString();
    }

    @Override
    public String getUsername() {
        return nameEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEditText.getText().toString();
    }

    @Override
    public void showRegistrationError() {
        Toast.makeText(getApplicationContext(), R.string.registration_failed_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainScreen() {
        Intent intent = MainActivity.getStartIntent(getActivity());
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showAutologinError() {
        Toast.makeText(getApplicationContext(),
                R.string.autologin_failed_on_registration_error,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public PresenterFactory<RegistrationPresenter> getPresenterFactory() {
        return () -> registrationPresenter;
    }
}
