package com.reactivespringwebflux.controller;

import com.reactivespringwebflux.document.Employee;
import com.reactivespringwebflux.repository.EmployeeReactiveRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeReactiveRepository employeeReactiveRepository;
    private final Employee employeeSample = Employee.builder().employeeName("John").id("1").salary(Double.valueOf("2200.00")).build();

    @Test
    @DisplayName("getAllEmployees returns Flux ")
    public void testGetAllEmployees_success(){
        BDDMockito.when(employeeReactiveRepository.findAll())
                .thenReturn(Flux.just(employeeSample));
        StepVerifier.create(employeeController.getAllEmployees())
                .expectSubscription()
                .expectNext(new Employee[]{employeeSample})
                .verifyComplete();

    }

    @Test
    @DisplayName("getAllEmployees returns null ")
    public void testGetAllEmployees_empty(){
        BDDMockito.when(employeeReactiveRepository.findAll())
                .thenReturn(Flux.empty());
        StepVerifier.create(employeeController.getAllEmployees())
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();

    }
}