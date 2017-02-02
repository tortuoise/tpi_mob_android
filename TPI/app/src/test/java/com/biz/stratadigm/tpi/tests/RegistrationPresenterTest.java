package com.biz.stratadigm.tpi.tests;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import biz.stratadigm.tpi.interactor.LoginInteractor;
import biz.stratadigm.tpi.interactor.RegistrationInteractor;
import biz.stratadigm.tpi.presenter.RegistrationPresenter;
import biz.stratadigm.tpi.ui.view.RegistrationView;
import rx.Observable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

public class RegistrationPresenterTest extends BasePresenterTest {

    @Mock
    LoginInteractor loginInteractorMock;

    @Mock
    RegistrationInteractor registrationInteractorMock;

    @Mock
    RegistrationView registrationViewMock;

    private RegistrationPresenter registrationPresenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        registrationPresenter = new RegistrationPresenter(getContext(),
                getAppSchedulers(),
                registrationInteractorMock,
                loginInteractorMock);
        registrationPresenter.takeView(registrationViewMock);
    }

    @Test
    public void shouldAutologinWhenRegistrationSucceeded() {
        String name = "testName";
        String password = "testPassword";
        String email = "testEmail";
        doReturn(Observable.just(null)).
                when(registrationInteractorMock).registerUser(eq(name), eq(email), eq(password));
        doReturn(name).when(registrationViewMock).getUsername();
        doReturn(password).when(registrationViewMock).getPassword();
        doReturn(email).when(registrationViewMock).getEmail();

        registrationPresenter.onRegisterButtonClicked();

        verify(loginInteractorMock).login(name, password);
    }

    @Test
    public void shouldShowErrorWhenNetworkErrorOccurs() {
        doReturn(Observable.error(new IOException()))
                .when(registrationInteractorMock).registerUser(anyString(), anyString(), anyString());

        registrationPresenter.onRegisterButtonClicked();

        verify(registrationViewMock).showNetworkError();
    }

    @Test
    public void shouldShowErrorWhenUnexpectedErrorOccurs() {
        doReturn(Observable.error(new Exception()))
                .when(registrationInteractorMock).registerUser(anyString(), anyString(), anyString());

        registrationPresenter.onRegisterButtonClicked();

        verify(registrationViewMock).showRegistrationError();
    }

    @Test
    public void shouldShowMainScreenWhenAutologinSucceeded() {
        doReturn(Observable.just(null)).
                when(registrationInteractorMock).registerUser(anyString(), anyString(), anyString());
        doReturn(Observable.just(null))
                .when(loginInteractorMock).login(anyString(), anyString());

        registrationPresenter.onRegisterButtonClicked();

        verify(registrationViewMock).showMainScreen();
    }
}
