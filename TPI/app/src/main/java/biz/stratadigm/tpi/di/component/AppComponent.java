package biz.stratadigm.tpi.di.component;

import android.content.Context;
import android.app.Activity;

import biz.stratadigm.tpi.di.module.AppModule;
import biz.stratadigm.tpi.di.module.NetworkModule;
import biz.stratadigm.tpi.di.scope.PerApplication;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import biz.stratadigm.tpi.manager.AppSchedulers;
import dagger.Component;

@PerApplication
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    Context context();

    AppSchedulers appSchedulers();

    AppPreferences appPreferences();

    ApiInterface apiInterface();

    void inject(Activity mainActivity);
}
