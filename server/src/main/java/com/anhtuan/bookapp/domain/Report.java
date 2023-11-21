package com.anhtuan.bookapp.domain;

import com.anhtuan.bookapp.request.AddReportRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = Report.REPORT_COLLECTION)
public class Report extends Domain{
    public static final String REPORT_COLLECTION = "report";
    public static final String USER_ID = "user_id";
    public static final String BOOK_ID = "book_id";
    public static final String TYPE = "type";
    public static final String CONTENT = "content";
    public static final String STATUS = "status";
    public static final String REPORT_TIME = "rp_time";
    public static final String CHECK_TIME = "ck_time";

    @Field(USER_ID)
    private String userId;

    @Field(BOOK_ID)
    private String bookId;

    @Field(TYPE)
    private Integer type;

    @Field(CONTENT)
    private String content;

    @Field(STATUS)
    private Integer status;

    @Field(REPORT_TIME)
    private Long reportTime;

    @Field(CHECK_TIME)
    private Long checkTime;

    public Report(AddReportRequest request) {
        this.bookId = request.getBookId();
        this.type = request.getType();
        this.content = request.getContent();
    }
}
