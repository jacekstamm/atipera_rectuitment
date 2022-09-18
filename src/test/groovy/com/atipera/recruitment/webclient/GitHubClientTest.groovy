package com.atipera.recruitment.webclient

import com.atipera.recruitment.model.github.GitHubRepositoryDto
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static com.atipera.recruitment.utils.GitHubConstants.getGitHubRepositoryArray

@SpringBootTest
class GitHubClientTest extends Specification {

    private static final String LOGIN_1 = 'test_login_1'

    private static final String REPOSITORY_NAME_1 = 'repositoryTestName_1'
    private static final String REPOSITORY_NAME_2 = 'repositoryTestName_2'

    private static final String BRANCH_URL_1 = 'https://www.testUrl_1.com'
    private static final String BRANCH_URL_2 = 'https://www.testUrl_2.com'

    private static final WRONG_LOGIN = 'wrongLogin'
    private static final WRONG_INPUT = '{{input}}'

    @Value('${github.repos.url}')
    private String GITHUB_REPOS_URL_BEGIN;

    @Value('${github.repos.url.end}')
    private String GITHUB_REPOS_URL_END;

    @Value('${github.branches.url}')
    private String GITHUB_BRANCHES_URL_BEGIN;

    @Value('${github.branches.url.end}')
    private String GITHUB_BRANCHES_URL_END;

    @MockBean
    private RestTemplate restTemplate

    @Autowired
    private GithubClient githubClient

    def 'should receive user GitHub repositories array'() {
        given:
        Mockito.when(restTemplate.getForObject(GITHUB_REPOS_URL_BEGIN + LOGIN_1 + GITHUB_REPOS_URL_END, GitHubRepositoryDto[].class))
                .thenReturn(getGitHubRepositoryArray())

        when:
        List<GitHubRepositoryDto> gitHubRepositoryList = githubClient.getGitHubRepository(LOGIN_1)

        then:
        verifyAll {
            gitHubRepositoryList.get(0).getName() == REPOSITORY_NAME_1
            gitHubRepositoryList.get(0).getOwner().getLogin() == LOGIN_1
            gitHubRepositoryList.get(0).getBranches_url() == BRANCH_URL_1
            !gitHubRepositoryList.get(0).isFork()

            gitHubRepositoryList.get(1).getName() == REPOSITORY_NAME_2
            gitHubRepositoryList.get(1).getOwner().getLogin() == LOGIN_1
            gitHubRepositoryList.get(1).getBranches_url() == BRANCH_URL_2
            gitHubRepositoryList.get(1).isFork()
        }
    }
}
