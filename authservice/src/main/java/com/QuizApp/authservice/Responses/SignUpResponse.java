package com.QuizApp.authservice.Responses;

import com.QuizApp.authservice.Domains.User;

public class SignUpResponse {
    private String username;

    private String email;

    private String phone;

    public SignUpResponse(User user){
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.username = user.getUsername();
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
