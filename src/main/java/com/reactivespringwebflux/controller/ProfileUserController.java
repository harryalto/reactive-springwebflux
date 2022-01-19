package com.reactivespringwebflux.controller;

import com.reactivespringwebflux.document.UserModel;
import com.reactivespringwebflux.handler.ProfileUserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.reactivespringwebflux.constants.EmployeeConstants.PROFILE_USERS_ENDPOINT_V1;


@RestController
@Slf4j
public class ProfileUserController {
    @Autowired
    private ProfileUserHandler profileUserHandler;

    @GetMapping(PROFILE_USERS_ENDPOINT_V1)
    public Flux<UserModel> getProfileUsers(@RequestParam("id") int[] itemIds) {
        return profileUserHandler.getBulkUsers(itemIds);
    }

}
