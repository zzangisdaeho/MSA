package com.example.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

    //Java Bean 등록에 의한 설정이 우선되어 해당 Route정보로 동작하게 된다. (property설정은 무시되거나 덮어씌워진다)
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r ->
                        r.path("/first-service/**")
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                                        .addResponseHeader("first-response", "first-response-header")
                                        .addResponseHeader("first-response2", "first-response-header2")
                        )
                        .uri("http://localhost:8081"))
                .route(r ->
                        r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                        .addResponseHeader("second-response", "second-response-header")
                        )
                        .uri("http://localhost:8082"))
                .build();
    }
}
