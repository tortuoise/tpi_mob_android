package biz.stratadigm.tpi.presenter;

import android.content.Context;

import biz.stratadigm.tpi.interactor.LoginInteractor;
import biz.stratadigm.tpi.interactor.RegistrationInteractor;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.ui.view.RegistrationView;
import rx.Observable;

public class RegistrationPresenter extends BasePresenter<RegistrationView> {

    private final RegistrationInteractor registrationInteractor;
    private final LoginInteractor loginInteractor;

    public RegistrationPresenter(Context applicationContext,
                                 AppSchedulers appSchedulers,
                                 RegistrationInteractor registrationInteractor,
                                 LoginInteractor loginInteractor) {
        super(applicationContext, appSchedulers);
        this.registrationInteractor = registrationInteractor;
        this.loginInteractor = loginInteractor;
    }

    public void onRegisterButtonClicked() {
        String username = getView().getUsername();
        String email = getView().getEmail();
        String password = getView().getPassword();

        Observable<Void> observable = registrationInteractor.registerUser(username, email, password);
        executeRequest(observable,
                new RegistrationSubscriber(username, password));
    }

    private class RegistrationSubscriber extends SimpleSubscriber<Void> {

        private final String username;
        private final String password;

        public RegistrationSubscriber(String username, String password) {

            this.username = username;
            this.password = password;
        }

        @Override
        public void onNext(Void aVoid) {
            doAutoLogin(username, password);
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

    private class LoginSubscriber extends SimpleSubscriber<Void> {

        @Override
        public void onNext(Void aVoid) {
            getView().showMainScreen();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            getView().showAutologinError();
        }
    }

    private void doAutoLogin(String username, String password) {
        executeRequest(loginInteractor.login(username, password), new LoginSubscriber());
    }
}
