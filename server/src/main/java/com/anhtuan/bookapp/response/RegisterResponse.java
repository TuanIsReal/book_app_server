package com.anhtuan.bookapp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private String token;
    private String refreshToken;
    private Integer role;

}
