package com.biz.stratadigm.tpi.interactor;

import com.biz.stratadigm.tpi.entity.dto.LoginDTO;
import com.biz.stratadigm.tpi.manager.ApiInterface;

import rx.Observable;

public class LoginInteractor {

    private final ApiInterface apiInterface;

    public LoginInteractor(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Observable<Void> login(String login, String password) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(login);
        loginDTO.setPassword(password);

        return apiInterface.login(loginDTO)
                .flatMap(loginResponseDTO -> apiInterface.checkToken());
    }
}
