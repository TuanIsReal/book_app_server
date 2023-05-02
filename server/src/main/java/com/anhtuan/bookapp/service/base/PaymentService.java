package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Payment;

public interface PaymentService {
    void addPayment(Payment payment);
    Payment findPaymentByTransactionIdAndStatus(String transactionId, int status);
    Payment findPaymentByTransactionId(String transactionId);
    void updatePaymentByTransactionId(String transactionId, int status, long payTime);
}
