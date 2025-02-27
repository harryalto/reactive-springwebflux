package com.reactivespringwebflux.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Employee {

    @Id
    private String id;
    private String employeeName;
    private Double salary;
    
}
