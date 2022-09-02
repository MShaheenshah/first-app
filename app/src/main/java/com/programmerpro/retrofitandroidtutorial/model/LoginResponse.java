package com.programmerpro.retrofitandroidtutorial.model;

public class LoginResponse {
    boolean error;
    String message;
    User user;

    public LoginResponse(boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
