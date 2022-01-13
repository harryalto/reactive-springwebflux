package com.reactivespringwebflux.handler;

import com.reactivespringwebflux.client.FamilyClient;
import com.reactivespringwebflux.document.Parent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class FamilyHandler {

    @Autowired
    private FamilyClient familyClient;

    public Flux<Parent> getFamiliesHierarchy() {

        return familyClient.getAllParents()
                .log()
                .flatMap(parent ->
                        familyClient.getAllChildsList(parent.getId())
                                .log()
                                .flatMap(childList -> familyClient.getChildsWithKids(childList))
                                .log()
                                .map(childList ->
                                        parent.toBuilder().children(childList).build()
                                )

                );
    }

    public Mono<Parent> getSingleParentHierarchy(final Integer parentId) {
        return familyClient.getParentById(parentId)
                .zipWhen(parent -> familyClient.getAllChildsList(parentId)
                        .log()
                        .flatMap(childList -> familyClient.getChildsWithKids(childList))
                )
                .map(tuple -> tuple.getT1().toBuilder().children(tuple.getT2()).build());
    }
}
