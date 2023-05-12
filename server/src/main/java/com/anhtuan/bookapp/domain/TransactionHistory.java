package com.anhtuan.bookapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = TransactionHistory.TRANSACTION_HISTORY_COLLECTION)
public class TransactionHistory {

    public static final String TRANSACTION_HISTORY_COLLECTION = "transaction_history";
    public static final String USER_ID = "user_id";
    public static final String POINT = "point";
    public static final String TRANSACTION_TYPE = "transaction_type";
    public static final String TRANSACTION_TIME = "transaction_time";

    @Id
    private String id;

    @Field(USER_ID)
    private String userId;

    @Field(POINT)
    private int point;

    @Field(TRANSACTION_TYPE)
    private int transactionType;

    @Field(TRANSACTION_TIME)
    private long transactionTime;

    public TransactionHistory(String userId, int point, int transactionType, long transactionTime) {
        this.userId = userId;
        this.point = point;
        this.transactionType = transactionType;
        this.transactionTime = transactionTime;
    }
}
