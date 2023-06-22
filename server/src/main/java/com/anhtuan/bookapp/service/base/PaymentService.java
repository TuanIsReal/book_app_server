package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Payment;

import java.util.List;

public interface PaymentService {
    void addPayment(Payment payment);
    Payment findPaymentByTransactionIdAndStatus(String transactionId, int status);
    Payment findPaymentByTransactionId(String transactionId);
    void updatePaymentByTransactionId(String transactionId, int status, String payDate, long payTime);
    List<Payment> getIncomeAdmin(long start, long end);
}
