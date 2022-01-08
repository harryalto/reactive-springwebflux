package com.reactivespringwebflux.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    private Integer projectId;
    private String projectName;
    private String projectDesc;
    private List<TeamMember> teamMemberList;

}
