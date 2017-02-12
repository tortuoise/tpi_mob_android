package biz.stratadigm.tpi.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import javax.inject.Inject;

import biz.stratadigm.tpi.interactor.BaseInteractor;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.ui.view.MenuView;

public class MenuPresenter extends BasePresenter<MenuView> {
    
    private static final String TAG = "TPI";
    
    @Inject BaseInteractor menuInteractor;

    public MenuPresenter(Context applicationContext,
                              AppSchedulers appSchedulers,
                              BaseInteractor menuInteractor) {
        super(applicationContext, appSchedulers);
        this.menuInteractor = menuInteractor;
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        //executeRequest(menuInteractor.logout(), new MenuSubscriber());

    }

    public void logout() {
        executeRequest(menuInteractor.logout(), new MenuSubscriber());
            getView().showStartScreen();
    }

    private class MenuSubscriber extends SimpleSubscriber<Void> {

        @Override
        public void onNext(Void avoid) {
        Log.v(TAG, "Finishing");
            getView().showStartScreen();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if (isNetworkError(e)) {
                getView().showNetworkError();
            } else if (isHttpErrorWithCode(e, HttpURLConnection.HTTP_UNAUTHORIZED)) {
                getView().showAuthError();
            } else {
                getView().showUnexpectedError();
            }
        }
    }
}
