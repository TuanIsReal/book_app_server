package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.TransactionHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionHistoryRepository extends MongoRepository<TransactionHistory, String> {
    List<TransactionHistory> getTransactionHistoriesByUserId(String userId);
}
