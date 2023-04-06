package com.len.service.impl;

import org.springframework.stereotype.Service;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.BlogCategory;
import com.len.mapper.BlogCategoryMapper;
import com.len.service.BlogCategoryService;

@Service
public class BlogCategoryServiceImpl extends BaseServiceImpl<BlogCategoryMapper, BlogCategory>
    implements BlogCategoryService {

}
