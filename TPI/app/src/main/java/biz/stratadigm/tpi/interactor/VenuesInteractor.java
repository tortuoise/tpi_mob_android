package biz.stratadigm.tpi.interactor;

import biz.stratadigm.tpi.entity.dto.VenueDTO;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import rx.Observable;
import java.util.ArrayList;

public class VenuesInteractor extends BaseInteractor {
    //private final ApiInterface apiInterface;
    //private final AppPreferences appPreferences;

    public VenuesInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        //this.apiInterface = apiInterface;
        //this.appPreferences = appPreferences;
        super(apiInterface, appPreferences);
    }

    public Observable<ArrayList<VenueDTO>> getVenues(int offset) {
        return getApiInterface().getVenues(offset);
    }
}
