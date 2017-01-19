package biz.stratadigm.tpi.di.module;

import android.content.Context;

import biz.stratadigm.tpi.di.scope.PerApplication;
import biz.stratadigm.tpi.manager.AppPreferences;
import biz.stratadigm.tpi.manager.AppSchedulers;
import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
}
