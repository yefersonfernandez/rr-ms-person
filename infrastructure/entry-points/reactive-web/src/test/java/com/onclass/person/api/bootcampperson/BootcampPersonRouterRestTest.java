package com.onclass.person.api.bootcampperson;

import com.onclass.person.api.config.BootcampPersonPath;
import com.onclass.person.api.dto.request.BootcampPersonRequestDto;
import com.onclass.person.api.dto.response.BootcampPersonResponseDto;
import com.onclass.person.api.mapper.BootcampPersonMapper;
import com.onclass.person.enums.ExceptionStatusCode;
import com.onclass.person.model.bootcampperson.BootcampPerson;
import com.onclass.person.usecase.bootcampperson.BootcampPersonUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@TestPropertySource(properties = {
        "routes.paths.enroll-person=/bootcamp/api/v1/enroll-person"
})
@ContextConfiguration(classes = {BootcampPersonRouterRest.class, BootcampPersonHandler.class, BootcampPersonPath.class})
@WebFluxTest
class BootcampPersonRouterRestTest {
    private static final String ENROLL_PERSON_PATH = "/bootcamp/api/v1/enroll-person";
    private static final Long PERSON_ID = 1L;
    private static final Long BOOTCAMP_ID = 10L;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BootcampPersonPath bootcampPersonPath;

    @MockitoBean
    private BootcampPersonUseCase bootcampPersonUseCase;
    @MockitoBean
    private BootcampPersonMapper bootcampPersonMapper;

    private BootcampPersonRequestDto validRequestDto;
    private BootcampPerson bootcampPerson;
    private BootcampPersonResponseDto bootcampPersonResponseDto;

    @BeforeEach
    void setUp() {
        validRequestDto = new BootcampPersonRequestDto(PERSON_ID.toString(), BOOTCAMP_ID.toString());
        bootcampPerson = BootcampPerson.builder()
                .personId(PERSON_ID)
                .bootcampId(BOOTCAMP_ID)
                .build();
        bootcampPersonResponseDto = new BootcampPersonResponseDto(BOOTCAMP_ID.toString(), PERSON_ID.toString());
    }

    @Test
    @DisplayName("Should load path property from BootcampPersonPath")
    void shouldLoadBootcampPersonPathProperty() {
        Assertions.assertThat(bootcampPersonPath.getEnrollPerson()).isEqualTo(ENROLL_PERSON_PATH);
    }

    @Test
    @DisplayName("POST /enroll-person - listenEnrollPerson: should return 201 when enrollment is created")
    void post_enrollPerson_shouldReturnCreated() {
        when(bootcampPersonMapper.toModel(validRequestDto)).thenReturn(bootcampPerson);
        when(bootcampPersonUseCase.enrollPerson(bootcampPerson)).thenReturn(Mono.just(bootcampPerson));
        when(bootcampPersonMapper.toBootcampPersonResponseDto(bootcampPerson)).thenReturn(bootcampPersonResponseDto);

        webTestClient.post()
                .uri(ENROLL_PERSON_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new BootcampPersonRequestDto(PERSON_ID.toString(), BOOTCAMP_ID.toString()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ExceptionStatusCode.CREATED.status())
                .jsonPath("$.data.personId").isEqualTo(PERSON_ID.intValue())
                .jsonPath("$.data.bootcampId").isEqualTo(BOOTCAMP_ID.intValue());
    }
}
