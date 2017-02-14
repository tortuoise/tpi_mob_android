package biz.stratadigm.tpi.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import biz.stratadigm.tpi.interactor.VenuesInteractor;
import biz.stratadigm.tpi.presenter.VenuesPresenter;
import biz.stratadigm.tpi.ui.view.VenuesView;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import biz.stratadigm.tpi.entity.dto.VenueDTO;
    

public class VenuesPresenterTest extends BasePresenterTest {


    private VenuesPresenter venuesPresenter;
        
    @Mock
    private VenuesInteractor venuesInteractorMock;

    @Mock
    private VenuesView venuesViewMock;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        venuesPresenter = new VenuesPresenter(getContext(), getAppSchedulers(), venuesInteractorMock);
        venuesPresenter.takeView(venuesViewMock);
    }


    @Test
    public void shouldShowListOnCreation() {
        doReturn(Observable.just(new ArrayList<VenueDTO>()))
                .when(venuesInteractorMock).getVenues(0);

        venuesPresenter.onCreate(mock(Bundle.class));
        verify(venuesViewMock).showVenues(anyList());
    }

    @Test
    public void shouldShowErrorWhenUnexpectedErrorOccurs() {
        doReturn(Observable.error(new Exception()))
                .when(venuesInteractorMock).getVenues(0);

        venuesPresenter.onCreate(mock(Bundle.class));
        verify(venuesViewMock).showUnexpectedError();
    }

    @Test
    public void shouldShowStartScreenOnLogout() {
        doReturn(Observable.just(null))
                .when(venuesInteractorMock).logout();

        venuesPresenter.logout();
        verify(venuesViewMock).showStartScreen();
    }
}
