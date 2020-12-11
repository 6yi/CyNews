package com.cy.news.admin.interceptors;

import com.cy.news.common.Utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yj
 * @ClassName JWTInterceptor
 * @date 2020/12/6  0:52
 * @Version V1.0
 * @Description: TODO
 */

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        Claims claims = JWTUtils.verifiedJwt(token);

        if(claims.getId()!=null){
            map.put("status",true);
            return true;
        }else {
            map.put("status",false);
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
