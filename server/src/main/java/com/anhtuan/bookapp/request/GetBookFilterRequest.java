package com.anhtuan.bookapp.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Data
@NoArgsConstructor
public class GetBookFilterRequest {
    int sort;
    int order;
    int status;
    int post;
    List<String> category;
    int page;

    @Override
    public String toString() {
        return "GetBookFilterRequest{" +
                "sort=" + sort +
                ", order=" + order +
                ", status=" + status +
                ", post=" + post +
                ", category=" + category +
                ", page=" + page +
                '}';
    }
}
