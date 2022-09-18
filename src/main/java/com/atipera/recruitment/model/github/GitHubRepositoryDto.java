package com.atipera.recruitment.model.github;

import lombok.Data;

@Data
public class GitHubRepositoryDto {
    private String name;
    private GitHubOwnerDto owner;
    private boolean isFork;
    private String branches_url;
}
