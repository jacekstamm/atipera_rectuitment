package com.atipera.recruitment.model;

import com.atipera.recruitment.model.github.Branch;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RepositoryDto {

    private String repositoryName;
    private String ownerName;
    private List<Branch> branchList;
}
