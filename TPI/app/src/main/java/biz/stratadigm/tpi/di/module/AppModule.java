package biz.stratadigm.tpi.di.module;

import static android.content.Context.LOCATION_SERVICE;
import android.content.Context;
import android.location.LocationManager;

import biz.stratadigm.tpi.di.scope.PerApplication;
import biz.stratadigm.tpi.manager.AppPreferences;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.interactor.BaseInteractor;
import biz.stratadigm.tpi.presenter.MenuPresenter;
import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import javax.inject.Singleton;

@Module
public class AppModule {
    private final Context applicationContext;

    public AppModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @PerApplication
    Context provideApplicationContext() {
        return this.applicationContext;
    }

    @Provides
    @PerApplication
    AppPreferences provideAppPreferences(Context context) {
        return new AppPreferences(context);
    }

    @Provides
    @PerApplication
    AppSchedulers provideAppSchedulers() {
        return new AppSchedulers(AndroidSchedulers.mainThread(), Schedulers.io());
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager(){
        return (LocationManager) applicationContext.getSystemService(LOCATION_SERVICE);
    }

}
