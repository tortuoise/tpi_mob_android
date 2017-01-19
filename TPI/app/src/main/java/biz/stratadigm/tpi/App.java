package biz.stratadigm.tpi;

import android.app.Application;

import biz.stratadigm.tpi.di.component.AppComponent;
import biz.stratadigm.tpi.di.component.DaggerAppComponent;
import biz.stratadigm.tpi.di.module.AppModule;

public class App extends Application {
    private static App app;
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(app))
                    .build();
        }
        return appComponent;
    }
}
