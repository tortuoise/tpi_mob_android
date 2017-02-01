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
            Log.v(TAG, "VenueSubscriber.onNext");
            ArrayList<VenueVO> vvenues = new ArrayList();
            Log.v(TAG, ""+venues.size());
            for (VenueDTO v : venues) {
                //VenueVO vv = new VenueVO(v.getId(), v.getName(), 0, v.getLoc().getLat(), v.getLoc().getLng());
                VenueVO vv = new VenueVO(v.getId(), v.getName(), 0, 0f, 0f);
                vvenues.add(vv);
            }
            getView().showVenues(vvenues);
            Log.v(TAG, ""+vvenues.size());
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
