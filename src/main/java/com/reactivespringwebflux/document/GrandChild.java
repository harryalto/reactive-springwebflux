package com.reactivespringwebflux.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class GrandChild {
    private Integer id;
    private Integer childId;
    private String name;
    private String path;
}

