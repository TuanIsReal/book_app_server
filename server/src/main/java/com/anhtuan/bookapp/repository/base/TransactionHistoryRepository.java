package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.TransactionHistory;
import com.anhtuan.bookapp.repository.customize.TransactionHistoryCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionHistoryRepository extends MongoRepository<TransactionHistory, String>, TransactionHistoryCustomizeRepository {
    List<TransactionHistory> getTransactionHistoriesByUserIdOrderByTransactionTimeDesc(String userId);

    List<TransactionHistory> getTransactionHistoriesByUserId(String userId);
}
