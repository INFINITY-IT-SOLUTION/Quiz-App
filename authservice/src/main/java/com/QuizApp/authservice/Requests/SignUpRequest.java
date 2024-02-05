package com.QuizApp.authservice.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class SignUpRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String daten;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String Pays;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    // Getter and Setter methods

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDaten() {
        return daten;
    }

    public void setDaten(String daten) {
        this.daten = daten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPays() {
        return Pays;
    }

    public void setPays(String pays) {
        Pays = pays;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}