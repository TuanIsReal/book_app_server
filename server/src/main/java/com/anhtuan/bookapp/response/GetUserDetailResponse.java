package com.anhtuan.bookapp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDetailResponse {
    private int totalBook;
    private int totalPurchasedBook;
    private double purchasedPoint;
    private double earnedPoint;
}
