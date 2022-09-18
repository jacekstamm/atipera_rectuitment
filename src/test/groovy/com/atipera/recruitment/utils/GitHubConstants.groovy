package com.atipera.recruitment.utils

import com.atipera.recruitment.model.github.GitHubOwnerDto
import com.atipera.recruitment.model.github.GitHubRepositoryDto

class GitHubConstants {

    private static final String LOGIN_1 = 'test_login_1'

    private static final String REPOSITORY_NAME_1 = 'repositoryTestName_1'
    private static final String REPOSITORY_NAME_2 = 'repositoryTestName_2'

    private static final String BRANCH_URL_1 = 'https://www.testUrl_1.com'
    private static final String BRANCH_URL_2 = 'https://www.testUrl_2.com'

    static GitHubRepositoryDto[] getGitHubRepositoryArray() {
        return [new GitHubRepositoryDto(
                name: REPOSITORY_NAME_1,
                owner: new GitHubOwnerDto(
                        login: LOGIN_1
                ),
                isFork: false,
                branches_url: BRANCH_URL_1)
                , new GitHubRepositoryDto(
                name: REPOSITORY_NAME_2,
                owner: new GitHubOwnerDto(
                        login: LOGIN_1
                ),
                isFork: true,
                branches_url: BRANCH_URL_2
        )] as GitHubRepositoryDto[]
    }
}