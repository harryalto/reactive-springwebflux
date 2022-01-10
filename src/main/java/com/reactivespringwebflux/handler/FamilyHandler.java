package com.reactivespringwebflux.handler;

import com.reactivespringwebflux.document.Child;
import com.reactivespringwebflux.document.GrandChild;
import com.reactivespringwebflux.document.Parent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class FamilyHandler {

    @Autowired
    private WebClient webClient;

    public Flux<Parent> getAllParents() {
        return webClient
                .get()
                .uri("parents")
                .retrieve()
                .bodyToFlux(Parent.class);
    }

    public Flux<Child> getAllChilds(final Integer parentId) {
        return webClient
                .get()
                .uri("childs?parentId=" + parentId)
                .retrieve()
                .bodyToFlux(Child.class);
    }

    public Mono<List<Child>> getAllChildsList(final Integer parentId) {
        ParameterizedTypeReference<List<Child>> childList = new ParameterizedTypeReference<List<Child>>() {
        };
        return webClient
                .get()
                .uri("childs?parentId=" + parentId)
                .retrieve()
                .bodyToMono(childList);
    }

    public Flux<GrandChild> getGrandKids(final Integer childId) {
        return webClient
                .get()
                .uri("grandKids?childId=" + childId)
                .retrieve()
                .bodyToFlux(GrandChild.class);
    }

    public Mono<List<GrandChild>> getGrandKidsList(final Integer childId) {
        ParameterizedTypeReference<List<GrandChild>> grandKidsList = new ParameterizedTypeReference<List<GrandChild>>() {
        };
        return webClient
                .get()
                .uri("grandKids?childId=" + childId)
                .retrieve()
                .bodyToMono(grandKidsList);
    }

    private Mono<List<Child>> getChildsWithKids(final List<Child> childList) {
        return Flux.fromIterable(childList).flatMap(child ->
                Mono.zip(Mono.just(child), getGrandKidsList(child.getId()))
                        .map(tuple2 -> tuple2.getT1().toBuilder().grandChildren(tuple2.getT2()).build())
        ).collectList();
    }

    public Flux<Parent> getFamiliesHierarchy() {

        return getAllParents()
                .log()
                .flatMap(parent ->
                        getAllChildsList(parent.getId())
                                .log()
                                .flatMap(childList -> getChildsWithKids(childList))
                                .log()
                                .map(childList ->
                                        parent.toBuilder().children(childList).build()
                                )

                );
    }

}
