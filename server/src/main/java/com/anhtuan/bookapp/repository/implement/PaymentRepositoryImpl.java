package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.Payment;
import com.anhtuan.bookapp.repository.customize.PaymentCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PaymentRepositoryImpl implements PaymentCustomizeRepository {
    private MongoTemplate mongoTemplate;
    @Override
    public void updatePaymentByTransactionId(String transactionId, int status, long payTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Payment.TRANSACTION_ID).is(transactionId));
        Update update = new Update();
        update.set(Payment.STATUS, status);
        update.set(Payment.PAY_TIME, payTime);
        mongoTemplate.updateFirst(query, update, Payment.class);
    }
}
