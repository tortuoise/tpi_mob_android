package biz.stratadigm.tpi.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import biz.stratadigm.tpi.interactor.ThaliListInteractor;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.ui.view.ThaliListView;
import biz.stratadigm.tpi.entity.dto.ThaliDTO;
import biz.stratadigm.tpi.entity.converter.ThaliConverter;

public class ThaliListPresenter extends BasePresenter<ThaliListView> {

    private static final String TAG = "TPI";
    private final ThaliListInteractor thaliListInteractor;
    private long currentPosition = 0;
    private String currentVenueName;

    public ThaliListPresenter(Context applicationContext,
                              AppSchedulers appSchedulers,
                              ThaliListInteractor thaliListInteractor) {
        super(applicationContext, appSchedulers);
        this.thaliListInteractor = thaliListInteractor;
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        executeRequest(thaliListInteractor.getThalisByVenue(0, getCurrentPosition()), new ThalisSubscriber());

    }

    private class ThalisSubscriber extends SimpleSubscriber<ArrayList<ThaliDTO>> {
        @Override
        public void onNext(ArrayList<ThaliDTO> thalis) {
            showFilterLabel();
            getView().showThalis(ThaliConverter.toThaliVOs(thalis));
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if (isNetworkError(e)) {
                getView().showNetworkError();
            } else if (isHttpErrorWithCode(e, HttpURLConnection.HTTP_UNAUTHORIZED)) {
                getView().showAuthError();
            } else {
                Log.v(TAG, e.toString());
                getView().showUnexpectedError();
            }
        }
    }

    private void showFilterLabel() {
        getView().showThalisFilterLabel(currentVenueName);
    }

    public long getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(long currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getCurrentVenueName() {
        return currentVenueName;
    }

    public void setCurrentVenueName(String currentVenueName) {
        this.currentVenueName = currentVenueName;
    }

}
