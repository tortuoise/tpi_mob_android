package biz.stratadigm.tpi.interactor;

import biz.stratadigm.tpi.entity.dto.LoginDTO;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import rx.Observable;

public class LoginInteractor {

    private final ApiInterface apiInterface;
    private final AppPreferences appPreferences;

    public LoginInteractor(ApiInterface apiInterface, AppPreferences appPreferences) {
        this.apiInterface = apiInterface;
        this.appPreferences = appPreferences;
    }

    public Observable<Void> login(String login, String password) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(login);
        loginDTO.setPassword(password);

        return apiInterface.login(loginDTO)
                .doOnNext(loginResponseDTO -> appPreferences.setToken(loginResponseDTO.getToken()))
                .flatMap(loginResponseDTO -> apiInterface.checkToken());
    }
}
