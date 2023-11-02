package com.anhtuan.bookapp.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBannerWordRequest {
    List<String> words;
}
