package com.reactivespringwebflux.controller;

import com.reactivespringwebflux.document.Parent;
import com.reactivespringwebflux.handler.FamilyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.reactivespringwebflux.constants.EmployeeConstants.FAMILIES_ENDPOINT_V1;

@RestController
@Slf4j
public class FamilyController {

    @Autowired
    private FamilyHandler familyHandler;

    @GetMapping(FAMILIES_ENDPOINT_V1)
    public Flux<Parent> viewAllParents() {
        return familyHandler.getFamiliesHierarchy();
    }

}