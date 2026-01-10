package com.onclass.person.api.person;

import com.onclass.person.api.config.PersonPath;
import com.onclass.person.api.openapi.PersonOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
@RequiredArgsConstructor
public class PersonRouterRest {
    private final PersonPath personPath;

    @Bean
    public RouterFunction<ServerResponse> routerFunctionPerson(PersonHandler handler) {
        return route()
                .POST(personPath.getPersons(), handler::listenSavePerson, PersonOpenApi::savePerson)
                .build();
    }
}
