package com.reactivespringwebflux.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class Parent {
    private Integer id;
    private String name;
    private String path;
    private List<Child> children;

}
