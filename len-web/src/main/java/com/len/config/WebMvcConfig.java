package com.len.config;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * spring shiro
 */
@Configuration
@EnableSpringDataWebSupport
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${lenosp.imagePath}")
    private String imagePath;
    @Value("${lenosp.uploadPath}")
    private String filePath;

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    /**
     * 资源处理配置
     * 
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").setCachePeriod(0);
        registry.addResourceHandler("/plugin/**", "/static/**").addResourceLocations("classpath:/plugin/",
            "classpath:/static/");
        registry.addResourceHandler("/ftl/**").addResourceLocations("classpath:/ftl/", "classpath:/freemarker/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + imagePath,
            "classpath:/plugin/lenos/img/");
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + imagePath);
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + imagePath);
        super.addResourceHandlers(registry);
    }

    /**
     * 保留国际化
     * 
     * @return
     */
    @Bean
    public LocaleChangeInterceptor interceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     * 拦截器注册
     * 
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor());
    }


    @Bean
    public LocaleResolver resolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        return resolver;
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add( new PageableHandlerMethodArgumentResolver());
    }
}
