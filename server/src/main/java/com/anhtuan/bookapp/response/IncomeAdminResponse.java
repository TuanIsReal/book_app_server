package com.anhtuan.bookapp.response;

import com.anhtuan.bookapp.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeAdminResponse {
    private int totalPoint;
    private long totalMoney;
    private int transactionPoint;
    List<Payment> paymentList;
}
