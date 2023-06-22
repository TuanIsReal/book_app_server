package com.anhtuan.bookapp.response;

import com.anhtuan.bookapp.domain.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeMemberResponse {
    private int revenue;
    private int spend;
    private int change;
    private List<TransactionHistory> transactionHistoryList;
}
