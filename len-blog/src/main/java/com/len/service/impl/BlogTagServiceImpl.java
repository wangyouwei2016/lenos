package com.len.service.impl;

import org.springframework.stereotype.Service;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.BlogTag;
import com.len.mapper.BlogTagMapper;
import com.len.service.BlogTagService;

@Service
public class BlogTagServiceImpl extends BaseServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {}
