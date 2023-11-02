package com.anhtuan.bookapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domain {
    public static final String ID = "_id";

    @Id
    private String id;
}
