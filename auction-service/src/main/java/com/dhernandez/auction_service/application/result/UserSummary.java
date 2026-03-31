package com.dhernandez.auction_service.application.result;

public class UserSummary {
    private final Long userId;
    private final String email;
    private final String userName;
    private final String role;

    public UserSummary(Long userId,String email,String userName,String role){
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.role = role;
    }

    public Long getUserId(){
        return userId;
    }
    public String getEmail(){
        return email;
    }
    public String getUserName(){
        return userName;
    }
    public String getRole(){
        return role;
    }
}
