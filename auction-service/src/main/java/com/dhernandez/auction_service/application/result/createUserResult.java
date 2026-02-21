package com.dhernandez.auction_service.application.result;

import com.dhernandez.auction_service.domain.model.Enum.EnumRoleUser;

public class createUserResult {
    private String idUser;
    private String email;
    private String userName;
    private boolean verified;
    private EnumRoleUser role;

    public createUserResult(String idUser, String email, String userName, boolean verified, EnumRoleUser role){
        this.idUser = idUser;
        this.email = email;
        this.userName = userName;
        this.verified = verified;
        this.role = role;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public boolean getVerified() {
        return verified;
    }

    public EnumRoleUser getRole(){
        return role;    
    }
}
