package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.Payment;

import java.util.List;

public interface PaymentCustomizeRepository {
    void updatePaymentByTransactionId(String transactionId, int status, String payDate, long payTime);
    List<Payment> findByStatusAndPayTime(int status, long start, long end);
}
