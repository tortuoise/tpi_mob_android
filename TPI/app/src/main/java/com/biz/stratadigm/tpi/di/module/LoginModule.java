package com.biz.stratadigm.tpi.di.module;

import android.content.Context;

import com.biz.stratadigm.tpi.di.scope.PerActivity;
import com.biz.stratadigm.tpi.interactor.LoginInteractor;
import com.biz.stratadigm.tpi.manager.ApiInterface;
import com.biz.stratadigm.tpi.manager.AppPreferences;
import com.biz.stratadigm.tpi.manager.AppSchedulers;
import com.biz.stratadigm.tpi.presenter.LoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @PerActivity
    @Provides
    LoginPresenter provideLoginPresenter(Context context, AppSchedulers appSchedulers, LoginInteractor loginInteractor) {
        return new LoginPresenter(context, appSchedulers, loginInteractor);
    }

    @PerActivity
    @Provides
    LoginInteractor provideLoginInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        return new LoginInteractor(apiInterface, appPreferences);
    }
}
