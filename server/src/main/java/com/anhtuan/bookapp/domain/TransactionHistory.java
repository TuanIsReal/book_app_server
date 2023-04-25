package com.anhtuan.bookapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = TransactionHistory.TRANSACTION_HISTORY_COLLECTION)
public class TransactionHistory {

    public static final String TRANSACTION_HISTORY_COLLECTION = "transaction_history";
    public static final String USER_ID = "user_id";
    public static final String POINT = "point";
    public static final String PURCHASE_WAY = "purchase_way";
    public static final String PURCHASE_TYPE = "purchase_type";
    public static final String TRANSACTION_TIME = "transaction_time";

    @Id
    private String id;

    @Field(USER_ID)
    private String userId;

    @Field(POINT)
    private String point;

    @Field(PURCHASE_WAY)
    private String purchaseWay;

    @Field(PURCHASE_TYPE)
    private String purchaseType;

    @Field(TRANSACTION_TIME)
    private String transactionTime;

    public TransactionHistory() {
    }

    public TransactionHistory(String id, String userId, String point, String purchaseWay, String purchaseType, String transactionTime) {
        this.id = id;
        this.userId = userId;
        this.point = point;
        this.purchaseWay = purchaseWay;
        this.purchaseType = purchaseType;
        this.transactionTime = transactionTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPurchaseWay() {
        return purchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        this.purchaseWay = purchaseWay;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }
}
