package com.anhtuan.bookapp.common;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.CustomUserDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "tuanisreal";
    private final String JWT_REFRESH_SECRET = "tuanisreal_refresh";

    private final long TOKEN_EXPIRATION = Constant.A_MINUTE * 30;

    private final long REFRESH_TOKEN_EXPIRATION = Constant.A_DAY * 7;

    private Algorithm algorithmToken = Algorithm.HMAC512(JWT_SECRET);
    private Algorithm algorithmRefreshToken = Algorithm.HMAC512(JWT_REFRESH_SECRET);


    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRATION);
        String token = null;

        try {
            token = JWT.create()
                    .withSubject(userDetails.getUser().getId())
                    .withExpiresAt(expiryDate)
                    .withIssuer("BookApp")
                    .sign(algorithmToken);
        } catch (JWTCreationException exception){

        }

        return token;
    }

    public String generateRefreshToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);
        String token = null;

        try {
            token = JWT.create()
                    .withSubject(userDetails.getUser().getId())
                    .withExpiresAt(expiryDate)
                    .withIssuer("BookApp")
                    .sign(algorithmRefreshToken);
        } catch (JWTCreationException exception){

        }

        return token;
    }

    public String getUserIdFromJWT(String token) {
        DecodedJWT jwt = JWT.require(algorithmToken)
                .build()
                .verify(token);

        return jwt.getSubject();
    }

    public String getUserIdFromRefreshToken(String refreshToken) {
        DecodedJWT jwt = JWT.require(algorithmRefreshToken)
                .build()
                .verify(refreshToken);

        return jwt.getSubject();
    }

    public boolean validateToken(String authToken) {
        try{
            JWTVerifier verifier = JWT.require(algorithmToken)
                    .withIssuer("BookApp")
                    .build();

            verifier.verify(authToken);
            return true;
        } catch (JWTVerificationException exception){
            System.out.println("Invalid JWT");
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try{
            JWTVerifier verifier = JWT.require(algorithmRefreshToken)
                    .withIssuer("BookApp")
                    .build();

            verifier.verify(refreshToken);
            return true;
        } catch (JWTVerificationException exception){
            System.out.println("Invalid JWT");
            return false;
        }
    }
}
