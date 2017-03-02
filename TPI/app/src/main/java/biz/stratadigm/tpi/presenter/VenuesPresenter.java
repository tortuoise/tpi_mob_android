package biz.stratadigm.tpi.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import java.net.HttpURLConnection;
import java.util.List;
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
    private int offset = 0;
    private ArrayList<VenueDTO> cache;
    private VenuesFilterType currentFilter = VenuesFilterType.ALL_VENUES;

    public VenuesPresenter(Context applicationContext,
                              AppSchedulers appSchedulers,
                              VenuesInteractor venuesInteractor) {
        super(applicationContext, appSchedulers);
        this.venuesInteractor = venuesInteractor;
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        executeRequest(venuesInteractor.getVenues(offset), new VenuesSubscriber());

    }

    public void refresh() {

        executeRequest(venuesInteractor.getVenues(offset), new VenuesSubscriber());
        try {
            showFilterLabel();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }

    }

    public void logout() {
        executeRequest(venuesInteractor.logout(), new MenuSubscriber());
            //getView().showStartScreen();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) { 
        this.offset = offset;
    }

    public List<VenueDTO> getCache() {
        return cache;
    }

    private class VenuesSubscriber extends SimpleSubscriber<ArrayList<VenueDTO>> {

        @Override
        public void onNext(ArrayList<VenueDTO> venues) {
            cache = venues;
            try {
                showFilterLabel();
            } catch (Exception e) {
                Log.v(TAG, e.toString());
            }
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

    private class MenuSubscriber extends SimpleSubscriber<Void> {

        @Override
        public void onNext(Void avoid) {
            getView().showStartScreen();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if (isNetworkError(e)) {
                getView().showNetworkError();
            } else if (isHttpErrorWithCode(e, HttpURLConnection.HTTP_UNAUTHORIZED)) {
                getView().showStartScreen(); // go back to start screen regarless of error
                //getView().showAuthError();
            } else {
                //getView().showUnexpectedError();
                getView().showStartScreen(); // go back to start screen regarless of error

            }
        }
    }

    private void showFilterLabel() {
        switch (currentFilter) {
            case NEARBY_VENUES:
                getView().showNearbyFilterLabel();
                break;
            case MINE_VENUES:
                getView().showMineFilterLabel();
                break;
            default:
                getView().showAllFilterLabel();
                break;
         }
      }

    public VenuesFilterType getFiltering() {
        return currentFilter;
    }

    public void setFiltering(VenuesFilterType filter) {
        this.currentFilter = filter;
    }
}
