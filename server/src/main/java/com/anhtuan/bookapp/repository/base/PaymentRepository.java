package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Payment;
import com.anhtuan.bookapp.repository.customize.PaymentCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String>, PaymentCustomizeRepository {
    Payment findPaymentByTransactionIdAndStatus(String transactionId, int status);
    Payment findPaymentByTransactionId(String transactionId);
}
