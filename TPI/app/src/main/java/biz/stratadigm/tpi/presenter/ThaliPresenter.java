package biz.stratadigm.tpi.presenter;

import android.content.Context;

import java.net.HttpURLConnection;

import biz.stratadigm.tpi.interactor.ThaliInteractor;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.ui.view.ThaliView;
import biz.stratadigm.tpi.entity.dto.ThaliDTO;

public class ThaliPresenter extends BasePresenter<ThaliView> {
    private final ThaliInteractor thaliInteractor;

    public ThaliPresenter(Context applicationContext, AppSchedulers appSchedulers, ThaliInteractor thaliInteractor) {
        super(applicationContext, appSchedulers);
        this.thaliInteractor = thaliInteractor;
    }

    public void onCreateButtonClicked() {
        String name = getView().getName();
        String region = getView().getRegion();
        String target = getView().getTarget();
        Boolean limited = getView().getLimited();
        Integer price = getView().getPrice();
        Long venue = getView().getVenue();
        executeRequest(thaliInteractor.createThali(name, region, target, limited, price, venue), new ThaliSubscriber());
    }

    public void onEditButtonClicked() {
        Long id = getView().getIdent();
        String name = getView().getName();
        String region = getView().getRegion();
        String target = getView().getTarget();
        Boolean limited = getView().getLimited();
        Integer price = getView().getPrice();
        Long venue = getView().getVenue();
        Long userId = getView().getUserId();
        executeRequest(thaliInteractor.editThali(id, name, region, target, limited, price, venue), new ThaliSubscriber());
    }

    private class ThaliSubscriber extends SimpleSubscriber<ThaliDTO> {
        @Override
        public void onNext(ThaliDTO aThali) {
            getView().showTakePhoto(aThali.getId());
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
