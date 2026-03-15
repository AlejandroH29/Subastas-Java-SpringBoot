package com.dhernandez.auction_service.application.result;

public class VerifyEmailResult {
    private final String userId;
    private final String email;
    private final String userName;
    private final boolean verified;

    public VerifyEmailResult(String userId, String email, String userName, boolean verified) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.verified = verified;
    }

    public String getUserId() {
        return userId;
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
}
