package biz.stratadigm.tpi.di.module;

import android.content.Context;

import biz.stratadigm.tpi.di.scope.PerActivity;
import biz.stratadigm.tpi.interactor.LoginInteractor;
import biz.stratadigm.tpi.interactor.RegistrationInteractor;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.presenter.RegistrationPresenter;
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
                                                       RegistrationInteractor registrationInteractor,
                                                       LoginInteractor loginInteractor) {
        return new RegistrationPresenter(context, appSchedulers, registrationInteractor, loginInteractor);
    }

    @PerActivity
    @Provides
    LoginInteractor provideLoginInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        return new LoginInteractor(apiInterface, appPreferences);
    }
}
