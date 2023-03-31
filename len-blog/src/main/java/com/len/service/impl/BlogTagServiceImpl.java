package com.len.service.impl;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.BlogTag;
import com.len.mapper.BlogTagMapper;
import com.len.service.BlogTagService;
import org.springframework.stereotype.Service;


@Service
public class BlogTagServiceImpl extends BaseServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {
}
