package com.dhernandez.auction_service.api.dto;

public class CreateUserRequest {
    private String email;
    private String userName;
    private String password;

    public CreateUserRequest(String email, String userName, String password){
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
