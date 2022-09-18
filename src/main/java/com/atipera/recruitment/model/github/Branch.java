package com.atipera.recruitment.model.github;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Branch {

    private String branchName;
    private String lastCommitSha;
}
