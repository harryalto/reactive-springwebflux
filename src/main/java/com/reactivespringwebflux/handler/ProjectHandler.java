package com.reactivespringwebflux.handler;

import com.reactivespringwebflux.document.Project;
import com.reactivespringwebflux.document.TeamMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProjectHandler {

    @Autowired
    private WebClient webClient;

    public Flux<Project> getAllProjects() {

        return webClient
                .get()
                .uri("projects")
                .retrieve()
                .bodyToFlux(Project.class);
    }

    public Mono<List<TeamMember>> getTeamMembers(Integer id) {
        ParameterizedTypeReference<List<TeamMember>> listParameterizedTypeReference =
                new ParameterizedTypeReference<List<TeamMember>>() {
                };
        return webClient.get()
                .uri("" + id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        Mono.empty()
                )
                .bodyToMono(listParameterizedTypeReference)
                .log();
    }

    public Flux<Project> getProjectsWithTeamMembers() {
        return getAllProjects()
                .flatMap(project ->
                        Mono.zip(Mono.just(project),
                                getTeamMembers(project.getProjectId()).defaultIfEmpty(new ArrayList<TeamMember>()))
                .map(tuple -> {
                    log.info("data" + tuple.getT2().size());
                    return Project.builder().projectId(tuple.getT1().getProjectId())
                            .projectName(tuple.getT1().getProjectName())
                            .projectDesc(tuple.getT1().getProjectDesc())
                            .teamMemberList(tuple.getT2()).build();
                }));

    }
}
