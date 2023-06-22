package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.Payment;
import com.anhtuan.bookapp.repository.customize.PaymentCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PaymentRepositoryImpl implements PaymentCustomizeRepository {
    private MongoTemplate mongoTemplate;
    @Override
    public void updatePaymentByTransactionId(String transactionId, int status, String payDate, long payTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Payment.TRANSACTION_ID).is(transactionId));
        Update update = new Update();
        update.set(Payment.STATUS, status);
        update.set(Payment.PAY_DATE, payDate);
        update.set(Payment.PAY_TIME, payTime);
        mongoTemplate.updateFirst(query, update, Payment.class);
    }

    @Override
    public List<Payment> findByStatusAndPayTime(int status, long start, long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Payment.STATUS).is(status)
                .and(Payment.PAY_TIME).gte(start).lt(end));
        return mongoTemplate.find(query, Payment.class);
    }
}
