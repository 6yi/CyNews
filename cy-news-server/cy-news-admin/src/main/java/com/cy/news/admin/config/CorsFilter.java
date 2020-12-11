package com.cy.news.admin.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yj
 * @ClassName CorsFilter
 * @date 2020/12/7  23:59
 * @Version V1.0
 * @Description: TODO
 */

public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setHeader("*", "*");

            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods",
                    "POST, GET, OPTIONS, DELETE,PUT");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            httpServletResponse.setHeader("Access-Control-Allow-Headers",
                    "Content-Type, x-requested-with, X-Custom-Header, token");
        }

  //      addMapping("/**")
//                //允许远端访问的域名
//                .allowedOrigins("http://localhost:8080")
//                //允许请求的方法("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                .allowedMethods("*")
//                //允许请求头
//                .allowedHeaders("*")
        System.out.println("crosfilter");
        chain.doFilter(request, response);
    }

}