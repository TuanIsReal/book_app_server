package com.anhtuan.bookapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@Document(collection = Payment.PAYMENT_COLLECTION)
public class Payment {
    public static final String PAYMENT_COLLECTION = "payment";
    public static final String ID = "_id";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String USER_ID = "user_id";
    public static final String TRANSACTION_INFO = "transaction_info";
    public static final String POINT = "point";
    public static final String MONEY = "money";
    public static final String UNIT = "unit";
    public static final String BANK_CODE = "bank_code";
    public static final String STATUS = "status";
    public static final String TRANSACTION_TIME = "transaction_time";
    public static final String PAY_DATE = "pay_date";
    public static final String PAY_TIME = "pay_time";

    @Id
    private String id;

    @Field(TRANSACTION_ID)
    private String transactionId;

    @Field(USER_ID)
    private String userId;

    @Field(TRANSACTION_INFO)
    private String transactionInfo;

    @Field(POINT)
    private int point;

    @Field(MONEY)
    private long money;

    @Field(UNIT)
    private String unit;

    @Field(BANK_CODE)
    private String bankCode;

    @Field(STATUS)
    private int status;

    @Field(TRANSACTION_TIME)
    private String transactionTime;

    @Field(PAY_DATE)
    private String patDate;

    @Field(PAY_TIME)
    private long payTime;

}
