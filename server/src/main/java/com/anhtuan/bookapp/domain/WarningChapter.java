package com.anhtuan.bookapp.domain;

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
@Document(collection = WarningChapter.WARNING_CHAPTER_COLLECTION)
public class WarningChapter extends Domain{
    public static final String WARNING_CHAPTER_COLLECTION = "warning_chapter";
    public static final String CHAPTER = "chapter";
    public static final String CHAPTER_REPORT = "chapter_report";
    public static final String SIMILAR_DOCUMENT = "similar_document";
    public static final String SIMILAR_WORD = "similar_word";

    @Field(CHAPTER)
    private String chapter;

    @Field(CHAPTER_REPORT)
    private String chapterReport;

    @Field(SIMILAR_DOCUMENT)
    private Double similarDocument;

    @Field(SIMILAR_WORD)
    private Double similarWord;
}
