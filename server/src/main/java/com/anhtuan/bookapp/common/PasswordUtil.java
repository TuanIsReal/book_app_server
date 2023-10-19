package com.anhtuan.bookapp.common;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Slf4j
public class PasswordUtil {
    private static SecretKeyFactory factory;
    private static SecureRandom random;

    static {
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            random = new SecureRandom();
        } catch (NoSuchAlgorithmException ex) {
            log.error("NoSuchAlgorithmException", ex);
            throw new RuntimeException(ex);
        }
    }

    private PasswordUtil() {
    }

    public static String encryptPassword(String password) {
        byte[] hash;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), "tuanisreal".getBytes(), 212212, 128);
        try {
            hash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return Utils.byteToString(hash);
    }
}
