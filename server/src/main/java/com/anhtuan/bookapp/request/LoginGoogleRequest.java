package com.anhtuan.bookapp.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginGoogleRequest{
    private String email;
    private String name;
    private String ip;
    private String img;

}
