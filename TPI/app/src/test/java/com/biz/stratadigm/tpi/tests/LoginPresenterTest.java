package com.biz.stratadigm.tpi.tests;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.HttpURLConnection;

import biz.stratadigm.tpi.interactor.LoginInteractor;
import biz.stratadigm.tpi.presenter.LoginPresenter;
import biz.stratadigm.tpi.ui.view.LoginView;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest extends BasePresenterTest {
    private LoginPresenter loginPresenter;

    @Mock
    private LoginInteractor loginInteractorMock;

    @Mock
    private LoginView loginViewMock;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        loginPresenter = new LoginPresenter(getContext(), getAppSchedulers(), loginInteractorMock);
        loginPresenter.takeView(loginViewMock);
    }

    @Test
    public void shouldShowMainScreenWhenLoginSucceeds() {
        doReturn(Observable.just(null))
                .when(loginInteractorMock).login(anyString(), anyString());

        loginPresenter.onLoginButtonClicked();

        verify(loginViewMock).showMainScreen();
    }

    @Test
    public void shouldShowErrorWhenNetworkErrorOccurs() {
        doReturn(Observable.error(new IOException()))
                .when(loginInteractorMock).login(anyString(), anyString());

        loginPresenter.onLoginButtonClicked();

        verify(loginViewMock).showNetworkError();
    }

    @Test
    public void shouldShowErrorWhenUnexpectedErrorOccurs() {
        doReturn(Observable.error(new Exception()))
                .when(loginInteractorMock).login(anyString(), anyString());

        loginPresenter.onLoginButtonClicked();

        verify(loginViewMock).showUnexpectedError();
    }

    @Test
    public void shouldShowErrorWhenCredentialsAreInvalid() {
        doReturn(Observable.error(createUnauthorizedException()))
                .when(loginInteractorMock).login(anyString(), anyString());

        loginPresenter.onLoginButtonClicked();

        verify(loginViewMock).showAuthError();
    }

    private HttpException createUnauthorizedException() {
        Response<String> response = Response.error(HttpURLConnection.HTTP_UNAUTHORIZED, mock(ResponseBody.class));
        return new HttpException(response);
    }
}
