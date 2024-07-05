package org.liuxing.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component //加上@Component注解交给spring生成Bean，全局过滤器才会生效
@Order(-1)  //@Order注解作用：传的数字越小，越先在过滤器链中执行
public class GatewayFiter implements GlobalFilter {
    /**
     * Gateway 全局过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //exchange中包含了ReQuest和ReSponse
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        //获取请求中所有的参数
        MultiValueMap<String,String> queryParams = serverHttpRequest.getQueryParams();
        //判断请求中是否含有username参数，参数为空则拦截下来设置状态码，在返回response,告诉已经被complete
        String username = queryParams.getFirst("username");
        if (username == null || username.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.NO_CONTENT);
        } else if (username.equals("1")){   //判断username参数是否为1，是1就放行
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        }
        return exchange.getResponse().setComplete();
    }
}
