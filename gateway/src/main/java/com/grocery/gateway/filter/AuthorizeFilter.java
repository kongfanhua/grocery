package com.grocery.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.grocery.common.enums.ResponseCodeEnum;
import com.grocery.common.exception.TokenAuthenticationException;
import com.grocery.common.model.ResponseResult;
import com.grocery.common.util.JWTUtil;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    @Value("${secretKey:123456}")
    private String secretKey;


    /**
     * 忽略过滤的路径
     */

    @Value("${com.cuslink.ignoreAuthUrls}")
    private String ignoreAuthUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        String path = serverHttpRequest.getURI().getPath();

        //  检查白名单（配置）
        if (decideIgnore(path)) {
            return chain.filter(exchange);
        }
        String token = serverHttpRequest.getHeaders().getFirst("token");
        if (StringUtils.isBlank(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return setResponseInfo(serverHttpResponse, ResponseCodeEnum.TOKEN_MISSION);
        }

        //todo 检查Redis中是否有此Token

        try {
            JWTUtil.verifyToken(token, secretKey);
        } catch (TokenAuthenticationException ex) {
            return setResponseInfo(serverHttpResponse, ResponseCodeEnum.TOKEN_INVALID);
        } catch (Exception ex) {
            return setResponseInfo(serverHttpResponse, ResponseCodeEnum.UNKNOWN_ERROR);
        }
        log.info("当前路径为" + path + ",校验失败!当前用户ID没有权限访问!");
        String userId = JWTUtil.getUserInfo(token);

        ServerHttpRequest mutableReq = serverHttpRequest.mutate().header("userId", userId).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
        return chain.filter(mutableExchange);
    }


    /**
     *
     * @param serverHttpResponse
     * @param responseCodeEnum
     * @return
     */
    private Mono<Void> setResponseInfo(ServerHttpResponse serverHttpResponse, ResponseCodeEnum responseCodeEnum) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ResponseResult responseResult = ResponseResult.error(responseCodeEnum.getCode(), responseCodeEnum.getMessage());
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(responseResult).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

    /**
     * 判断是否是忽略路径
     *
     * @param servletPath
     * @return
     */
    private boolean decideIgnore(String servletPath) {
        //跳过不需要验证的路径
        String[] ignoreUrl = this.ignoreAuthUrls.split(",");
        for (String ignore : ignoreUrl) {
            if (ignore.equals(servletPath) || ignore.endsWith("**") && servletPath.contains(ignore.substring(0, ignore.length() - 2))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}