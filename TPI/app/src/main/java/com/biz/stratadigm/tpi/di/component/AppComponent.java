package com.biz.stratadigm.tpi.di.component;

import android.content.Context;

import com.biz.stratadigm.tpi.di.module.AppModule;
import com.biz.stratadigm.tpi.di.scope.PerApplication;
import com.biz.stratadigm.tpi.manager.AppPreferences;
import com.biz.stratadigm.tpi.manager.AppSchedulers;

import dagger.Component;

@PerApplication
@Component(modules = AppModule.class)
public interface AppComponent {
    Context context();

    AppSchedulers appSchedulers();

    AppPreferences appPreferences();
}
