package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.domain.Payment;
import com.anhtuan.bookapp.repository.base.PaymentRepository;
import com.anhtuan.bookapp.service.base.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;

    @Override
    public void addPayment(Payment payment) {
        paymentRepository.insert(payment);
    }

    @Override
    public Payment findPaymentByTransactionIdAndStatus(String transactionId, int status) {
        return paymentRepository.findPaymentByTransactionIdAndStatus(transactionId, status);
    }

    @Override
    public Payment findPaymentByTransactionId(String transactionId) {
        return paymentRepository.findPaymentByTransactionId(transactionId);
    }

    @Override
    public void updatePaymentByTransactionId(String transactionId, int status, String payDate, long payTime) {
        paymentRepository.updatePaymentByTransactionId(transactionId, status, payDate, payTime);
    }

    @Override
    public List<Payment> getIncomeAdmin(long start, long end) {
        return paymentRepository.findByStatusAndPayTime(TRANSACTION_STATUS.SUCCESS, start, end);
    }
}
