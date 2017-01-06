package com.biz.stratadigm.tpi.manager;

import com.biz.stratadigm.tpi.entity.dto.LoginDTO;
import com.biz.stratadigm.tpi.entity.dto.LoginResponseDTO;
import com.biz.stratadigm.tpi.entity.dto.RegisterDTO;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by vkiyako on 1/5/17.
 */

public interface ApiInterface {

    @POST("/token_auth")
    Observable<LoginResponseDTO> login(@Body LoginDTO loginDTO);

    @GET("/hello")
    Observable<Void> checkToken();

    @POST("/create/user")
    Observable<Void> registerUser(@Body RegisterDTO registerDTO);
}
