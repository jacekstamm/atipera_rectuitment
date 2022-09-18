package com.atipera.recruitment.webclient;

import com.atipera.recruitment.exception.UserNotFoundOnGithubException;
import com.atipera.recruitment.exception.WrongInputException;
import com.atipera.recruitment.model.github.GitHubBranchDto;
import com.atipera.recruitment.model.github.GitHubRepositoryDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GithubClient {

    @Value("${github.repos.url}")
    private String BASE_USER_INFO_URL;

    @Value("${github.repos.url.end}")
    private String BASE_USER_INFO_URL_END;

    @Value("${github.branches.url}")
    private String GITHUB_BRANCHES_URL;

    @Value("${github.branches.url.end}")
    private String GITHUB_BRANCHES_URL_END;

    @Autowired
    private RestTemplate restTemplate;

    public List<GitHubRepositoryDto> getGitHubRepository(String login) {
        try {
            String url = BASE_USER_INFO_URL+ login + BASE_USER_INFO_URL_END;
            GitHubRepositoryDto[] githubRepositoryList = restTemplate.getForObject(url, GitHubRepositoryDto[].class);
            assert githubRepositoryList != null;
            return Arrays.asList(githubRepositoryList);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundOnGithubException(login);
        } catch (IllegalArgumentException exception) {
        throw new WrongInputException(login);
    }
    }

    public Map<String, List<GitHubBranchDto>> getRepositoryBranchesMap(List<GitHubRepositoryDto> gitHubRepositoryList) {
        Map<String, List<GitHubBranchDto>> branchDtoHashMap = new HashMap<>();
        for (GitHubRepositoryDto githubRepository : gitHubRepositoryList) {
            GitHubBranchDto[] gitHubBranchDtos = restTemplate.getForObject(githubRepository.getBranches_url(), GitHubBranchDto[].class);
            assert gitHubBranchDtos != null;
            branchDtoHashMap.put(githubRepository.getBranches_url(), Arrays.asList(gitHubBranchDtos));
        }
        return branchDtoHashMap;
    }
}
