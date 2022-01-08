package com.reactivespringwebflux.controller;

import com.reactivespringwebflux.document.Project;
import com.reactivespringwebflux.handler.ProjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.reactivespringwebflux.constants.EmployeeConstants.PROJECT_ENDPOINT_V1;

@RestController
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectHandler projectHandler;

    @GetMapping(PROJECT_ENDPOINT_V1)
    public Flux<Project> getAllProjects() {
        return projectHandler.getProjectsWithTeamMembers();
    }

}
