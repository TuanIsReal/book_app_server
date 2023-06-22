package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.TransactionHistory;
import com.anhtuan.bookapp.repository.base.TransactionHistoryRepository;
import com.anhtuan.bookapp.service.base.TransactionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public void addTransactionHistory(TransactionHistory transactionHistory) {
        transactionHistoryRepository.insert(transactionHistory);
    }

    @Override
    public List<TransactionHistory> findIncomeMember(String userId, long start, long end) {
        return transactionHistoryRepository.findIncomeMemberByUserIdAndTime(userId, start, end);
    }

    @Override
    public List<TransactionHistory> findIncomeAdmin(long start, long end) {
        return transactionHistoryRepository.findIncomeAdminByUserIdAndTime(start, end);
    }

    @Override
    public List<TransactionHistory> getTransactionHistoryUser(String userId) {
        return transactionHistoryRepository.getTransactionHistoriesByUserIdOrderByTransactionTimeDesc(userId);
    }
}
