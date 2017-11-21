package biz.stratadigm.tpi.interactor;

import biz.stratadigm.tpi.entity.dto.ThaliDTO;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import rx.Observable;
import java.util.ArrayList;

public class ThaliListInteractor extends BaseInteractor{
    //private final ApiInterface apiInterface;
    //private final AppPreferences appPreferences;

    public ThaliListInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        //this.apiInterface = apiInterface;
        //this.appPreferences = appPreferences;
        super(apiInterface, appPreferences);
    }

    public Observable<ArrayList<ThaliDTO>> getThalisByVenue(int offset, long venueid) {
        return getApiInterface().getThalisByVenue(offset, venueid);
    }
}
