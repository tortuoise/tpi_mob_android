package biz.stratadigm.tpi.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import biz.stratadigm.tpi.interactor.VenuesInteractor;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.ui.view.VenuesView;
import biz.stratadigm.tpi.entity.dto.VenueDTO;
import biz.stratadigm.tpi.entity.vo.VenueVO;
import biz.stratadigm.tpi.entity.converter.VenueConverter;

public class VenuesPresenter extends BasePresenter<VenuesView> {
    
    private static final String TAG = "TPI";
    private final VenuesInteractor venuesInteractor;

    public VenuesPresenter(Context applicationContext,
                              AppSchedulers appSchedulers,
                              VenuesInteractor venuesInteractor) {
        super(applicationContext, appSchedulers);
        Log.v(TAG, "VenuesPresenter: ctor");
        this.venuesInteractor = venuesInteractor;
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        Log.v(TAG, "VenuesPresenter: onCreate");

        executeRequest(venuesInteractor.getVenues(0), new VenuesSubscriber());

    }

    private class VenuesSubscriber extends SimpleSubscriber<ArrayList<VenueDTO>> {

        @Override
        public void onNext(ArrayList<VenueDTO> venues) {
            getView().showVenues(VenueConverter.toVenueVOs(venues));
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
}
