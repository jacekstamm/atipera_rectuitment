package com.atipera.recruitment.model.github;

import lombok.Getter;

@Getter
public class GitHubBranchDto {

    private String name;
    private GitHubCommitDto commit;
}
