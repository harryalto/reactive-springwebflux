package com.reactivespringwebflux.client;

import com.reactivespringwebflux.document.Child;
import com.reactivespringwebflux.document.GrandChild;
import com.reactivespringwebflux.document.Parent;
import com.reactivespringwebflux.exception.DataNotFoundException;
import com.reactivespringwebflux.exception.Generic4xxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@Slf4j
public class FamilyClient {

    private final WebClient webClient;

    public FamilyClient(WebClient.Builder builder,
                        @Value("${clients.family.url}") String usersBaseUrl) {
        this.webClient = builder.baseUrl(usersBaseUrl).build();
    }

    public Flux<Parent> getAllParents() {
        return this.webClient
                .get()
                .uri("parents")
                .retrieve()
                .bodyToFlux(Parent.class);
    }

    public Mono<Parent> getParentById(final Integer parentId) {
        return this.webClient
                .get()
                .uri("parents/" + parentId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    if (response.statusCode() == NOT_FOUND) {
                        log.error("data not found for the passed id");
                        return Mono.error(new DataNotFoundException(parentId));
                    }
                    return Mono.error(new Generic4xxException("Not Able to Process Request"));
                })
                .bodyToMono(Parent.class)
                .log();
    }

    public Mono<List<Child>> getAllChildsList(final Integer parentId) {
        ParameterizedTypeReference<List<Child>> childList = new ParameterizedTypeReference<List<Child>>() {
        };
        return this.webClient
                .get()
                .uri("childs?parentId=" + parentId)
                .retrieve()
                .bodyToMono(childList);
    }

    public Mono<List<GrandChild>> getGrandKidsList(final Integer childId) {
        ParameterizedTypeReference<List<GrandChild>> grandKidsList = new ParameterizedTypeReference<List<GrandChild>>() {
        };
        return this.webClient
                .get()
                .uri("grandKids?childId=" + childId)
                .retrieve()
                .bodyToMono(grandKidsList);
    }

    public Mono<List<Child>> getChildsWithKids(final List<Child> childList) {
        return Flux.fromIterable(childList).flatMap(child ->
                Mono.zip(Mono.just(child), getGrandKidsList(child.getId()))
                        .map(tuple2 -> tuple2.getT1().toBuilder().grandChildren(tuple2.getT2()).build())
        ).collectList();
    }
}
