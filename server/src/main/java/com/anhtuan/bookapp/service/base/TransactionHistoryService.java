package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.TransactionHistory;

import java.util.List;


public interface TransactionHistoryService {
    void addTransactionHistory(TransactionHistory transactionHistory);
    List<TransactionHistory> findIncomeMember(String userId, long start, long end);
    List<TransactionHistory> findIncomeAdmin(long start, long end);
    List<TransactionHistory> getTransactionHistoryUser(String userId);
    List<TransactionHistory> getIncomeWriter();
    List<TransactionHistory> getRechargedUser();
    List<TransactionHistory> getSpendMoneyUser();
}
