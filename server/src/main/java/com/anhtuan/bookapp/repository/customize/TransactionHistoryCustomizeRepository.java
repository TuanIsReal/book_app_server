package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.TransactionHistory;

import java.util.List;

public interface TransactionHistoryCustomizeRepository {
    List<TransactionHistory> findIncomeMemberByUserIdAndTime(String userId, long start, long end);
    List<TransactionHistory> findIncomeAdminByUserIdAndTime(long start, long end);
    List<TransactionHistory> getIncomeWriter();
    List<TransactionHistory> getRechargedUser();
    List<TransactionHistory> getSpendMoneyUser();
}
