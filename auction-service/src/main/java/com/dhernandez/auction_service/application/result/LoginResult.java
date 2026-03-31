package com.dhernandez.auction_service.application.result;

public class LoginResult {
    private final String accessToken;
    private final String tokenType;
    private final UserSummary user;
    
    public LoginResult(String accessToken,String tokenType, UserSummary user){
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.user = user;
    }

    public String getAccessToken(){
        return accessToken;
    }
    public String getTokenType(){
        return tokenType;
    }
    public UserSummary getUserData(){
        return user;
    }
}
