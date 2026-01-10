package com.onclass.person.api.bootcampperson;

import com.onclass.person.api.config.BootcampPersonPath;
import com.onclass.person.api.openapi.BootcampPersonOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
@RequiredArgsConstructor
public class BootcampPersonRouterRest {
    private final BootcampPersonPath bootcampPersonPath;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(BootcampPersonHandler handler) {
        return route()
                .POST(bootcampPersonPath.getEnrollPerson(), handler::listenEnrollPerson, BootcampPersonOpenApi::enrollPerson)
                .build();
    }
}
