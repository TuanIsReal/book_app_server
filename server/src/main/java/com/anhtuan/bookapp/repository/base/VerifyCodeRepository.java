package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.VerifyCode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerifyCodeRepository extends MongoRepository<VerifyCode, String> {
    VerifyCode findVerifyCodeByCodeAndType(String code, int type);
    VerifyCode findVerifyCodeByCodeAndEmailAndTypeAndTimeGreaterThan(String code, String email, int type, long time);
    VerifyCode findVerifyCodeByCodeAndUserIdAndTypeAndTimeGreaterThan(String code, String userId, int type, long time);
}
