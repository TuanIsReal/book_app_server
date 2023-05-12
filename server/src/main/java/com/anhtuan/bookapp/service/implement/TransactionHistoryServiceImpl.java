package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.TransactionHistory;
import com.anhtuan.bookapp.repository.base.TransactionHistoryRepository;
import com.anhtuan.bookapp.service.base.TransactionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public void addTransactionHistory(TransactionHistory transactionHistory) {
        transactionHistoryRepository.insert(transactionHistory);
    }
}
