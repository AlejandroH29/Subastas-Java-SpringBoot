package com.dhernandez.auction_service.application.command;


public class createUserCommand {
    private String email;
    private String userName;
    private String password;

    public createUserCommand(String email, String userName, String password){
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
