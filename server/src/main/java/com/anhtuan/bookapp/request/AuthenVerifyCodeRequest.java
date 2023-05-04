package com.anhtuan.bookapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenVerifyCodeRequest {
    private String email;
    private String userId;
    private int type;
    private String code;
}
