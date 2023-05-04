package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.VerifyCode;

public interface VerifyCodeService {
    void addVerifyCode(VerifyCode verifyCode);
    VerifyCode findVerifyCodeByCodeAndType(String code, int type);
    VerifyCode findVerifyCodeByCodeAndEmailAndTypeAndTimeGreaterThan(String code, String email, int type, long time);
    VerifyCode findVerifyCodeByCodeAndUserIdAndTypeAndTimeGreaterThan(String code, String userId, int type, long time);
}
