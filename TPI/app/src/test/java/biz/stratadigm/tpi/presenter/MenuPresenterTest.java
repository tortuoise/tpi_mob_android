package biz.stratadigm.tpi.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import biz.stratadigm.tpi.interactor.BaseInteractor;
import biz.stratadigm.tpi.presenter.MenuPresenter;
import biz.stratadigm.tpi.ui.view.MenuView;
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

public class MenuPresenterTest extends BasePresenterTest {


    private MenuPresenter menuPresenter;
        
    @Mock
    private BaseInteractor menuInteractorMock;

    @Mock
    private MenuView menuViewMock;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        menuPresenter = new MenuPresenter(getContext(), getAppSchedulers(), menuInteractorMock);
        menuPresenter.takeView(menuViewMock);
    }

    @Test
    public void shouldShowStartScreenOnSuccess() {
        doReturn(Observable.just(null))
                .when(menuInteractorMock).logout();

        menuPresenter.logout();
        verify(menuViewMock).showStartScreen();
    }

}

