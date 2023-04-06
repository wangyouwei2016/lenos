package com.len.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("简单文章")
public class SimpleArticle {

    private String code;

    private String title;
}
