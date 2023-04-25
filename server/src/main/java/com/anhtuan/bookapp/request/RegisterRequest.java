package com.anhtuan.bookapp.request;

import javax.validation.constraints.NotNull;

public class RegisterRequest extends BaseRequest{

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String ip;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String name, String ip) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.ip = ip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
