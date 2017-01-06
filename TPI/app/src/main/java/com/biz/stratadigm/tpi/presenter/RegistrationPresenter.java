package com.biz.stratadigm.tpi.presenter;

import android.content.Context;

import com.biz.stratadigm.tpi.interactor.RegistrationInteractor;
import com.biz.stratadigm.tpi.manager.AppSchedulers;
import com.biz.stratadigm.tpi.ui.view.RegistrationView;

public class RegistrationPresenter extends BasePresenter<RegistrationView> {

    private final RegistrationInteractor registrationInteractor;

    public RegistrationPresenter(Context applicationContext,
                                 AppSchedulers appSchedulers,
                                 RegistrationInteractor registrationInteractor) {
        super(applicationContext, appSchedulers);
        this.registrationInteractor = registrationInteractor;
    }

    public void onRegisterButtonClicked() {
        String username = getView().getUsername();
        String email = getView().getEmail();
        String password = getView().getPassword();

        executeRequest(registrationInteractor.registerUser(username, email, password),
                new RegistrationSubscriber());
    }

    private class RegistrationSubscriber extends SimpleSubscriber<Void> {

        @Override
        public void onNext(Void aVoid) {
            getView().showMainScreen();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if (isNetworkError(e)) {
                getView().showNetworkError();
            } else {
                getView().showRegistrationError();
            }
        }
    }
}
