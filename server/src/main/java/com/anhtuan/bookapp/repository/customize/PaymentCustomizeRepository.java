package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.Payment;

public interface PaymentCustomizeRepository {
    void updatePaymentByTransactionId(String transactionId, int status, long payTime);
}
