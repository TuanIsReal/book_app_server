package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.TransactionHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionHistoryRepository extends MongoRepository<TransactionHistory, String> {
}
