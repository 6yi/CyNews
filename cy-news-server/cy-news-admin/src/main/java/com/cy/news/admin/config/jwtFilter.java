package com.cy.news.admin.config;

import cn.hutool.core.util.StrUtil;
import com.cy.news.common.Utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yj
 * @ClassName jwtFilter
 * @date 2020/12/8  0:02
 * @Version V1.0
 * @Description: TODO
 */
public class jwtFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("jwtfilter");
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("token");
        token=StrUtil.sub(token,1,-1);
        System.out.println(token);
        Map<String, Object> map = new HashMap<>();
        Claims claims = null;
        try {
            claims = JWTUtils.verifiedJwt(token);
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            response.getWriter().println("jwt error");
        }


    }
}
