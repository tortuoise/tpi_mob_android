package com.biz.stratadigm.tpi.entity.dto;

import com.google.gson.annotations.SerializedName;

public class LoginResponseDTO {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
