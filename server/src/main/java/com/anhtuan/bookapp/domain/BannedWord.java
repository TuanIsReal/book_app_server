package com.anhtuan.bookapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = BannedWord.BANNED_WORD_COLLECTION)
public class BannedWord extends Domain{
    public static final String BANNED_WORD_COLLECTION = "banned_word";
    public static final String VERSION = "version";
    public static final String WORDS = "words";

    @Field(VERSION)
    private int version;

    @Field(WORDS)
    private List<String> words;
}
