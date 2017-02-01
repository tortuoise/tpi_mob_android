package biz.stratadigm.tpi.di.module;

import android.content.Context;

import biz.stratadigm.tpi.di.scope.PerActivity;
import biz.stratadigm.tpi.interactor.VenuesInteractor;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.presenter.VenuesPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class VenuesModule {

    @PerActivity
    @Provides
    VenuesPresenter provideVenuesPresenter(Context context, AppSchedulers appSchedulers, VenuesInteractor venuesInteractor) {
        return new VenuesPresenter(context, appSchedulers, venuesInteractor);
    }

    @PerActivity
    @Provides
    VenuesInteractor provideVenuesInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        return new VenuesInteractor(apiInterface, appPreferences);
    }
}
