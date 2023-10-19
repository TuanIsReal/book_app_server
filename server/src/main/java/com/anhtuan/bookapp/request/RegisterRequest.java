package com.anhtuan.bookapp.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RegisterRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private Integer role;

    @NotNull
    private String ip;

    public RegisterRequest(String email, String password, String name, String ip) {
        this.email = email;
        this.password = password;
        this.name = name;
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
