package com.len.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomErrorViewResolver implements ErrorViewResolver {

    private static final String PAGE_500 = "/error/500";
    private static final String PAGE_404 = "/error/404";
    private static final String PAGE_403 = "/error/403";
    private static final String OTHER_ERROR = "/error/error";

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        boolean isServerError = status.is5xxServerError();
        ModelAndView andView = new ModelAndView();
        andView.addObject("message", model.get("message"));

        if (status.value() == 404) {
            andView.setViewName(PAGE_404);
        } else if (status.value() == 403) {
            andView.setViewName(PAGE_403);
        } else if (isServerError) {
            andView.setViewName(PAGE_500);
        } else {
            andView.addObject("status", status.value());
            andView.setViewName(OTHER_ERROR);
        }
        return andView;

    }

}