package com.apirestangular.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@WebFilter(urlPatterns = "/api/*")
public class MyFilter implements Filter {
 
    @Override
    public void doFilter(ServletRequest request,
                        ServletResponse response,
                        FilterChain chain) throws IOException, ServletException
    {/*
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;*/
        System.out.println("Request URI is: " + ((HttpServletRequest) request).getRequestURI());
        chain.doFilter(request, response);
        System.out.println("Response Status Code is: " + ((HttpServletResponse) response).getStatus());
    }
}