package biz.stratadigm.tpi.interactor;

import biz.stratadigm.tpi.entity.dto.ThaliDTO;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import rx.Observable;

public class ThaliInteractor extends BaseInteractor {

    //private final ApiInterface apiInterface;
    //private final AppPreferences appPreferences;

    public ThaliInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        //this.apiInterface = apiInterface;
        //this.appPreferences = appPreferences;
        super(apiInterface, appPreferences);
    }

    public Observable<Void> createThali(String name, String region, String target, Boolean limited, Integer price, Long venue, Long userId) {
        ThaliDTO thaliDTO = new ThaliDTO();
        thaliDTO.setName(name);
        thaliDTO.setTarget(target);
        thaliDTO.setLimited(limited);
        thaliDTO.setRegion(region);
        thaliDTO.setPrice(price);
        thaliDTO.setUserId(userId);
        thaliDTO.setVenue(venue);

        return getApiInterface().createThali(thaliDTO);
                //.doOnNext(thaliDTO -> getAppPreferences().setToken(loginResponseDTO.getToken()));
                //.flatMap(loginResponseDTO -> getApiInterface().checkToken());
    }

    public Observable<Void> editThali(Long id, String name, String region, String target, Boolean limited, Integer price, Long venue, Long userId) {
        ThaliDTO thaliDTO = new ThaliDTO();
        thaliDTO.setId(id);
        thaliDTO.setName(name);
        thaliDTO.setTarget(target);
        thaliDTO.setLimited(limited);
        thaliDTO.setRegion(region);
        thaliDTO.setPrice(price);
        thaliDTO.setUserId(userId);
        thaliDTO.setVenue(venue);

        return getApiInterface().editThali(id.longValue(), thaliDTO);
                //.doOnNext(thaliDTO -> getAppPreferences().setToken(loginResponseDTO.getToken()));
                //.flatMap(loginResponseDTO -> getApiInterface().checkToken());
    }
}
