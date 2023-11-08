package com.anhtuan.bookapp.common;

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

    private final long JWT_EXPIRATION = 30000L;
    private Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);


    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        String token = null;

        try {
            token = JWT.create()
                    .withSubject(userDetails.getUser().getId())
                    .withClaim("Role", userDetails.getUser().getRole())
                    .withExpiresAt(expiryDate)
                    .withIssuer("BookApp")
                    .sign(algorithm);
        } catch (JWTCreationException exception){

        }

        return token;
    }

    public String getUserIdFromJWT(String token) {
        DecodedJWT jwt = JWT.require(algorithm)
                .build()
                .verify(token);

        return jwt.getSubject();
    }

    public boolean validateToken(String authToken) {
        try{
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("BookApp")
                    .build();

            verifier.verify(authToken);
            return true;
        } catch (JWTVerificationException exception){
            System.out.println("Invalid JWT");
            return false;
        }
    }
}
