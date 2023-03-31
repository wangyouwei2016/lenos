package com.len.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class VArticle {

    private String title;

    private String content;

    private List<String> category=new ArrayList<>();

    private List<String> tags=new ArrayList<>();
}
