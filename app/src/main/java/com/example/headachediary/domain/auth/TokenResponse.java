package com.example.headachediary.domain.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResponse{

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public String getAccess_token() {
        return this.accessToken; }
    public void setAccess_token(String access_token) {
        this.accessToken = access_token; }

    public String getToken_type() {
        return this.tokenType; }
    public void setToken_type(String token_type) {
        this.tokenType = token_type; }

}
