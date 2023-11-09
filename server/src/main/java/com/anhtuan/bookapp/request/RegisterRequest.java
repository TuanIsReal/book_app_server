package com.anhtuan.bookapp.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    private String email;

    private String password;

    private String name;

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
