package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.repository.base.TransactionHistoryRepository;
import com.anhtuan.bookapp.service.base.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;
}
