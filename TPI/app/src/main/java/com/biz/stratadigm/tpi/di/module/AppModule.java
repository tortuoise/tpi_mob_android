package com.biz.stratadigm.tpi.di.module;

import android.content.Context;

import com.biz.stratadigm.tpi.BuildConfig;
import com.biz.stratadigm.tpi.di.scope.PerApplication;
import com.biz.stratadigm.tpi.manager.AppPreferences;
import com.biz.stratadigm.tpi.manager.AppSchedulers;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
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

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
