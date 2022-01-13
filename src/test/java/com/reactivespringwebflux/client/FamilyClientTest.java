package com.reactivespringwebflux.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactivespringwebflux.document.Parent;
import com.reactivespringwebflux.exception.DataNotFoundException;
import com.reactivespringwebflux.exception.Generic4xxException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

class FamilyClientTest {

    private static MockWebServer mockBackEnd;
    private static FamilyClient familyClient;
    final ObjectMapper mapper = new ObjectMapper();


    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start(8090);
        familyClient = new FamilyClient(WebClient.builder(), mockBackEnd.url("/").toString());
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void getSingleParentById_success() throws JsonProcessingException {
        Parent mockParent = Parent.builder().name("George Bush Sr.").id(1).path("1").build();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(mapper.writeValueAsString(mockParent))
                .addHeader("Content-Type", "application/json"));

        Mono<Parent> parentMono = familyClient.getParentById(1);

        StepVerifier.create(parentMono)
                .expectNextMatches(parent -> parent.getId()
                        .equals(1))
                .verifyComplete();
    }

    @Test
    void getSingleParentById_NotFound() throws JsonProcessingException {
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "application/json"));

        Mono<Parent> parentMono = familyClient.getParentById(1);

        StepVerifier.create(parentMono)
                .expectError(DataNotFoundException.class)
                .verify();
    }

    @Test
    void getSingleParentById_Generic4XXError() throws JsonProcessingException {
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(429)
                .addHeader("Content-Type", "application/json"));

        Mono<Parent> parentMono = familyClient.getParentById(1);

        StepVerifier.create(parentMono)
                .expectError(Generic4xxException.class)
                .verify();
    }
}