package biz.stratadigm.tpi.di.module;

import android.content.Context;

import biz.stratadigm.tpi.di.scope.PerActivity;
import biz.stratadigm.tpi.interactor.ThaliInteractor;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.presenter.ThaliPresenter;
import biz.stratadigm.tpi.presenter.ThaliPicturePresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class ThaliModule {

    @PerActivity
    @Provides
    ThaliPresenter provideThaliPresenter(Context context, AppSchedulers appSchedulers, ThaliInteractor thaliInteractor) {
        return new ThaliPresenter(context, appSchedulers, thaliInteractor);
    }

    @PerActivity
    @Provides
    ThaliInteractor provideThaliInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        return new ThaliInteractor(apiInterface, appPreferences);
    }

    @PerActivity
    @Provides
    ThaliPicturePresenter provideThaliPicturePresenter(Context context, AppSchedulers appSchedulers, ThaliInteractor thaliInteractor) {
        return new ThaliPicturePresenter(context, appSchedulers, thaliInteractor);
    }
}
