package biz.stratadigm.tpi.interactor;

import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import rx.Observable;

public class BaseInteractor {

    private final ApiInterface apiInterface;
    private final AppPreferences appPreferences;

    public BaseInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        this.apiInterface = apiInterface;
        this.appPreferences = appPreferences;
    }

    public Observable<Void> logout() {

        return apiInterface.logout();
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    public AppPreferences getAppPreferences() {
        return appPreferences;
    }
}
