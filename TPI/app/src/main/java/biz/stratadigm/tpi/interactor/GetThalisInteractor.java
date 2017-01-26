package biz.stratadigm.tpi.interactor;

import biz.stratadigm.tpi.entity.dto.ThaliDTO;
import biz.stratadigm.tpi.manager.ApiInterface;
import rx.Observable;

public class GetThalisInteractor {
    private final ApiInterface apiInterface;

    public GetThalisInteractor(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Observable<ThaliDTO> getThalis(int offset) {
        return apiInterface.getThalis(offset);
    }
}
