package biz.stratadigm.tpi.di.module;

import android.content.Context;

import biz.stratadigm.tpi.di.scope.PerActivity;
import biz.stratadigm.tpi.interactor.ThaliListInteractor;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.presenter.ThaliListPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class ThaliListModule {

    @PerActivity
    @Provides
    ThaliListPresenter provideThaliListPresenter(Context context, AppSchedulers appSchedulers, ThaliListInteractor thaliListInteractor) {
        return new ThaliListPresenter(context, appSchedulers, thaliListInteractor);
    }

    @PerActivity
    @Provides
    ThaliListInteractor provideThaliListInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        return new ThaliListInteractor(apiInterface, appPreferences);
    }
}
