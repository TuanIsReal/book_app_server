package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.domain.TransactionHistory;
import com.anhtuan.bookapp.repository.customize.TransactionHistoryCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TransactionHistoryRepositoryImpl implements TransactionHistoryCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public List<TransactionHistory> findIncomeMemberByUserIdAndTime(String userId, long start, long end) {
        Query query = new Query();
        List<Integer> typeList = new ArrayList<>();
        typeList.add(TRANSACTION_TYPE.BUY_BOOK);
        typeList.add(TRANSACTION_TYPE.SELL_BOOK);
        query.addCriteria(Criteria.where(TransactionHistory.USER_ID).is(userId)
                .and(TransactionHistory.TRANSACTION_TYPE).in(typeList)
                .and(TransactionHistory.TRANSACTION_TIME).gte(start).lt(end));
        return mongoTemplate.find(query, TransactionHistory.class);
    }

    @Override
    public List<TransactionHistory> findIncomeAdminByUserIdAndTime(long start, long end) {
        Query query = new Query();
        List<Integer> typeList = new ArrayList<>();
        typeList.add(TRANSACTION_TYPE.BUY_BOOK);
        typeList.add(TRANSACTION_TYPE.SELL_BOOK);
        query.addCriteria(Criteria.where(TransactionHistory.TRANSACTION_TYPE).in(typeList)
                .and(TransactionHistory.TRANSACTION_TIME).gte(start).lt(end));
        return mongoTemplate.find(query, TransactionHistory.class);
    }

    @Override
    public List<TransactionHistory> getIncomeWriter(){
        Query query = new Query();
        query.addCriteria(Criteria.where(TransactionHistory.TRANSACTION_TYPE).is(TRANSACTION_TYPE.SELL_BOOK));
        return mongoTemplate.find(query, TransactionHistory.class);
    }

    @Override
    public List<TransactionHistory> getRechargedUser() {
        Query query = new Query();
        query.addCriteria(Criteria.where(TransactionHistory.TRANSACTION_TYPE).is(TRANSACTION_TYPE.RECHARGE_POINT));
        return mongoTemplate.find(query, TransactionHistory.class);
    }

    @Override
    public List<TransactionHistory> getSpendMoneyUser() {
        Query query = new Query();
        query.addCriteria(Criteria.where(TransactionHistory.TRANSACTION_TYPE).is(TRANSACTION_TYPE.BUY_BOOK));
        return mongoTemplate.find(query, TransactionHistory.class);
    }

}
