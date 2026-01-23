package com.onclass.person.api.person;

import com.onclass.person.api.config.PersonPath;
import com.onclass.person.api.dto.request.PersonRequestDto;
import com.onclass.person.api.dto.response.PersonResponseDto;
import com.onclass.person.api.mapper.PersonMapper;
import com.onclass.person.api.utils.ValidatorUtil;
import com.onclass.person.enums.ExceptionStatusCode;

import com.onclass.person.model.person.Person;
import com.onclass.person.usecase.person.PersonUseCase;
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
        "routes.paths.persons=/person/api/v1/persons",
        "routes.paths.personById=/person/api/v1/persons/{personId}"
})
@ContextConfiguration(classes = {PersonRouterRest.class, PersonHandler.class, PersonPath.class, ValidatorUtil.class})
@WebFluxTest
class PersonRouterRestTest {
    private static final String PERSONS_PATH = "/person/api/v1/persons";
    private static final String PERSON_BY_ID_PATH = "/person/api/v1/persons/{personId}";
    private static final String PERSON_NAME = "John";
    private static final String PERSON_EMAIL = "doe@example.com";
    private static final int AGE = 22;
    private static final Long PERSON_ID = 1L;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PersonPath personPath;

    @MockitoBean
    private PersonUseCase personUseCase;
    @MockitoBean
    private PersonMapper personMapper;
    @MockitoBean
    private ValidatorUtil validatorUtil;

    private PersonRequestDto validRequestDto;
    private Person person;
    private PersonResponseDto personResponseDto;

    @BeforeEach
    void setUp() {
        validRequestDto = new PersonRequestDto(PERSON_NAME, PERSON_EMAIL, AGE);
        person = Person.builder()
                .id(PERSON_ID)
                .name(PERSON_NAME)
                .email(PERSON_EMAIL)
                .build();
        personResponseDto = new PersonResponseDto(PERSON_ID, PERSON_NAME, PERSON_EMAIL,AGE);
    }

    @Test
    @DisplayName("Should load path property from PersonPath")
    void shouldLoadPersonPathProperty() {
        Assertions.assertThat(personPath.getPersons()).isEqualTo(PERSONS_PATH);
        Assertions.assertThat(personPath.getPersonById()).isEqualTo(PERSON_BY_ID_PATH);
    }

    @Test
    @DisplayName("POST /persons - listenSavePerson: should return 201 when person is created")
    void post_savePerson_shouldReturnCreated() {
        when(validatorUtil.validate(validRequestDto)).thenReturn(Mono.just(validRequestDto));
        when(personMapper.toModel(validRequestDto)).thenReturn(person);
        when(personUseCase.savePerson(person)).thenReturn(Mono.just(person));
        when(personMapper.toPersonResponseDto(person)).thenReturn(personResponseDto);

        webTestClient.post()
                .uri(PERSONS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validRequestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ExceptionStatusCode.CREATED.status())
                .jsonPath("$.data.name").isEqualTo(PERSON_NAME)
                .jsonPath("$.data.email").isEqualTo(PERSON_EMAIL);
    }

    @Test
    @DisplayName("GET /persons/{personId} - listenGetPersonById: should return 200 when person is found")
    void get_personById_shouldReturnOk() {
        when(personUseCase.getPersonById(PERSON_ID)).thenReturn(Mono.just(person));
        when(personMapper.toPersonResponseDto(person)).thenReturn(personResponseDto);

        webTestClient.get()
                .uri(PERSON_BY_ID_PATH.replace("{personId}", PERSON_ID.toString()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ExceptionStatusCode.OK.status())
                .jsonPath("$.data.name").isEqualTo(PERSON_NAME)
                .jsonPath("$.data.email").isEqualTo(PERSON_EMAIL);
    }
}

