package com.biz.stratadigm.tpi.di.module;

import android.content.Context;

import com.biz.stratadigm.tpi.di.scope.PerActivity;
import com.biz.stratadigm.tpi.interactor.RegistrationInteractor;
import com.biz.stratadigm.tpi.manager.ApiInterface;
import com.biz.stratadigm.tpi.manager.AppSchedulers;
import com.biz.stratadigm.tpi.presenter.RegistrationPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RegistrationModule {

    @PerActivity
    @Provides
    RegistrationInteractor provideRegistrationInteractor(ApiInterface apiInterface) {
        return new RegistrationInteractor(apiInterface);
    }

    @PerActivity
    @Provides
    RegistrationPresenter provideRegistrationPresenter(AppSchedulers appSchedulers,
                                                       Context context,
                                                       RegistrationInteractor registrationInteractor) {
        return new RegistrationPresenter(context, appSchedulers, registrationInteractor);
    }
}
