package com.cy.news.api_gateway.auth;

import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Utils.JWTUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName JWTFilter
 * @Author 6yi
 * @Date 2020/11/23 23:28
 * @Version 1.0
 * @Description:
 * JWT验证
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "jwtfilter")
public class JWTFilter implements GlobalFilter, Ordered {


    private  List<String> filterUrl;

    public  List<String> getFilterUrl() {
        return filterUrl;
    }

    public  void setFilterUrl(List<String> filterUrl) {
        this.filterUrl = filterUrl;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest httpRequest = exchange.getRequest();
        ServerWebExchange mutatedExchange=exchange;
        for (String url : filterUrl) {
            if(httpRequest.getURI().getPath().startsWith(url)){
                try {
                    String authority = httpRequest.getHeaders().getFirst("authority");
                    if(authority==null){
                        throw new Exception("not found token");
                    }
                    Claims claims = JWTUtils.verifiedJwt(authority);
                    log.info(claims.getId());
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().header("uId" ,claims.getId()).build();
                    mutatedExchange= exchange.mutate().request(mutatedRequest).build();
                } catch (Exception e){
                    e.printStackTrace();
                    ResultDTO resultDTO = ResultDTO.builder().code(403).data(e.getMessage()).build();
                    try {
                        return exchange.getResponse()
                                .writeWith(Flux.just(exchange.getResponse()
                                                                        .bufferFactory()
                                                                        .wrap(new ObjectMapper().writeValueAsBytes(resultDTO))));
                    } catch (JsonProcessingException jsonProcessingException) {
                        return exchange.getResponse()
                                .writeWith(Flux.just(exchange.getResponse()
                                                                        .bufferFactory()
                                                                        .wrap("error".getBytes())));
                    }
                }
            }
        }
        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
