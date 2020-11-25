package com.cy.news.api_gateway.auth;

import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Utils.JWTUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

/**
 * @ClassName JWTFilter
 * @Author 6yi
 * @Date 2020/11/23 23:28
 * @Version 1.0
 * @Description:
 * JWT验证
 */

@Component
@Slf4j
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

        for (String url : filterUrl) {
            if(httpRequest.getURI().getPath().equals(url)){
                try {
                    List<String> authoritys = httpRequest.getHeaders().get("authority");
                    if(authoritys==null){
                        throw new Exception("not found token");
                    }
                    String authority = authoritys.get(0);
                    JWTUtils.verifiedJwt(authority);
                } catch (Exception e){
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
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
