package com.dhernandez.auction_service.domain.model;

import com.dhernandez.auction_service.domain.exception.ErrorCreatingUser;
import com.dhernandez.auction_service.domain.model.Enum.EnumRoleUser;

public class User {
    private String idUser;
    private String email;
    private String userName;
    private String password;
    private boolean verified;
    private EnumRoleUser role;

    public User(String email, String userName, String password){
        if(email == null || email.isBlank()){
            throw new ErrorCreatingUser("El email no puede estar vacio");
        }
        email.trim().toLowerCase();
        String emailBasicRegex = ".+@.+";
        if(!email.matches(emailBasicRegex)){
            throw new ErrorCreatingUser("El email debe tener estructura minima: '@'");
        }
        
        if(userName == null || userName.isBlank()){
            throw new ErrorCreatingUser("El nombre de usuario no puede estar vacio");
        }
        if(userName.length() < 3){
            throw new ErrorCreatingUser("El nombre de usuario debe ser mas largo");
        }

        if(password == null || password.isBlank()){
            throw new ErrorCreatingUser("La contraseña no puede estar vacia");
        }
        if(password.length() < 8){
            throw new ErrorCreatingUser("La contraseña debe ser mas larga");
        }
        if(password.equals(email)){
            throw new ErrorCreatingUser("La contraseña y el correo no pueden ser iguales");
        }

        this.idUser = null;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.verified = false;
        this.role = EnumRoleUser.USER;
    }

    public User(String idUser, String email, String userName, String password, boolean verified, EnumRoleUser role){
        this.idUser = idUser;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.verified = verified;
        this.role = role;
    }

    public void verify(){
        if(this.verified == true){
            throw new ErrorCreatingUser("El usuario ya esta verificado");
        }
        this.verified = true;
    }

    public void changeEmail(String newEmail){
        String emailBasicRegex = ".+@.+";
        if(newEmail == null || newEmail.isBlank()){
            throw new ErrorCreatingUser("El email no puede estar vacio");
        }
        if(!newEmail.matches(emailBasicRegex)){
            throw new ErrorCreatingUser("El email debe tener estructura minima: '@'");
        }

        if(newEmail.equals(this.email)){
            return;
        }

        this.email = newEmail;
        this.verified = false;
    }

    public void changeUsername(String newUserName){
        if(newUserName == null || newUserName.isBlank()){
            throw new ErrorCreatingUser("El nombre de usuario no puede estar vacio");
        }
        if(newUserName.length() < 3){
            throw new ErrorCreatingUser("El nombre de usuario debe ser mas largo");
        }

        if(newUserName.equals(this.userName)){
            return;
        }

        this.userName = newUserName;
    }

    public void changePassword(String newPassword){
        if(newPassword == null || newPassword.isBlank()){
            throw new ErrorCreatingUser("La contraseña no puede estar vacia");
        }
        if(newPassword.length() < 8){
            throw new ErrorCreatingUser("La contraseña debe ser mas larga");
        }
        if(newPassword.equals(email)){
            throw new ErrorCreatingUser("La contraseña y el correo no pueden ser iguales");
        }
        if(newPassword.equals(this.password)){
            return;
        }
    
        this.password = newPassword;
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

    public String getPassword() {
        return password;
    }

    public boolean getVerified() {
        return verified;
    }

    public EnumRoleUser getRole(){
        return role;    
    }   
}
