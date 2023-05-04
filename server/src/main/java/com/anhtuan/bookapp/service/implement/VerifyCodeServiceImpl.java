package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.VerifyCode;
import com.anhtuan.bookapp.repository.base.VerifyCodeRepository;
import com.anhtuan.bookapp.service.base.VerifyCodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VerifyCodeServiceImpl implements VerifyCodeService {

    private VerifyCodeRepository verifyCodeRepository;

    @Override
    public void addVerifyCode(VerifyCode verifyCode) {
        verifyCodeRepository.insert(verifyCode);
    }

    @Override
    public VerifyCode findVerifyCodeByCodeAndType(String code, int type) {
        return verifyCodeRepository.findVerifyCodeByCodeAndType(code, type);
    }

    @Override
    public VerifyCode findVerifyCodeByCodeAndEmailAndTypeAndTimeGreaterThan(String code, String email, int type, long time) {
        return verifyCodeRepository.findVerifyCodeByCodeAndEmailAndTypeAndTimeGreaterThan(code, email, type, time);
    }

    @Override
    public VerifyCode findVerifyCodeByCodeAndUserIdAndTypeAndTimeGreaterThan(String code, String userId, int type, long time) {
        return verifyCodeRepository.findVerifyCodeByCodeAndUserIdAndTypeAndTimeGreaterThan(code, userId, type, time);
    }
}
