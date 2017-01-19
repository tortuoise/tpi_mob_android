package biz.stratadigm.tpi.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import biz.stratadigm.tpi.App;
import biz.stratadigm.tpi.BuildConfig;
import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.di.component.DaggerLoginComponent;
import biz.stratadigm.tpi.di.module.LoginModule;
import biz.stratadigm.tpi.presenter.LoginPresenter;
import biz.stratadigm.tpi.ui.activity.MainActivity;
import biz.stratadigm.tpi.ui.view.LoginView;
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
        return inflater.inflate(R.layout.fragment_login, container, false);
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
            emailEditText.setText("fateslav@gmail.com");
            passwordEditText.setText("test123");
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
