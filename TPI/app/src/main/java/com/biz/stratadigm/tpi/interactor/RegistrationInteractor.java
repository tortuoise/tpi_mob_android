package com.biz.stratadigm.tpi.interactor;

import com.biz.stratadigm.tpi.entity.dto.RegisterDTO;
import com.biz.stratadigm.tpi.manager.ApiInterface;

import rx.Observable;

public class RegistrationInteractor {
    private final ApiInterface apiInterface;

    public RegistrationInteractor(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Observable<Void> registerUser(String name, String email, String password) {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setName(name);
        registerDTO.setEmail(email);
        registerDTO.setPassword(password);

        return apiInterface.registerUser(registerDTO);
    }
}
