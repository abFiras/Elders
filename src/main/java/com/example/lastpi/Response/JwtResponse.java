package com.example.lastpi.Response;



import com.example.lastpi.Enum.Gender;
import com.example.lastpi.Enum.Status;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String firstname;
    private String lastename;
    private String cin;

    private String phone;
    private Gender gender;
    private Status status;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles, String firstname, String lastename, String cin, String phone, Gender gender, Status status) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.firstname=firstname;
        this.lastename=lastename;
        this.cin=cin;
        this.phone=phone;
        this.gender=gender;
        this.status=status;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getRoles() {
        return roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastename() {
        return lastename;
    }

    public String getCin() {
        return cin;
    }

    public String getPhone() {
        return phone;
    }
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}