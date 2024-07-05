package org.liuxing.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayFiter implements GlobalFilter {
    /**
     * Gateway 过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        MultiValueMap<String,String> queryParams = serverHttpRequest.getQueryParams();
        String username = queryParams.getFirst("username");
        if (username == null || username.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.NO_CONTENT);
        } else if (username.equals("1")){
            return chain.filter(exchange);
        }
        return exchange.getResponse().setComplete();
    }
}
