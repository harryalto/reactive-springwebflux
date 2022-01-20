package com.reactivespringwebflux.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactivespringwebflux.document.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ProfileUserHandler {

    @Autowired
    private WebClient webClient;

    private static final ObjectMapper mapper = new ObjectMapper();

    public Flux<UserModel> getBulkUsers(int[] ids) {
        List<Integer> list = Arrays.stream(ids).boxed().collect(Collectors.toList());
        return Flux.fromIterable(list).flatMap(this::getProfileUser);
    }

    public Mono<UserModel> getProfileUser(Integer id) {

        return webClient
                .get()
                .uri("profilesEndPoint/" + id)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode ->
                        jsonNode.path("data").path("resultholder").path("profiles").path("profileholder").path("user")
                ).map(
                        userjsonNode -> mapper.convertValue(userjsonNode, UserModel.class)
                );
    }


}
