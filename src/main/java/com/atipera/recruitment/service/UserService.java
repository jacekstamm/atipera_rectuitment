package com.atipera.recruitment.service;

import com.atipera.recruitment.mapper.UserMapper;
import com.atipera.recruitment.model.RepositoryDto;
import com.atipera.recruitment.model.github.GitHubBranchDto;
import com.atipera.recruitment.webclient.GithubClient;
import com.atipera.recruitment.model.github.GitHubRepositoryDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GithubClient githubClient;
    private final UserMapper userMapper;

    public List<RepositoryDto> getUserInfo(String login){
        List<GitHubRepositoryDto> allUserRepositoryList = githubClient.getGitHubRepository(login);
        List<GitHubRepositoryDto> userRepositoryWithoutForkList = removeForkFromUserRepos(allUserRepositoryList);
        Map<String, List<GitHubBranchDto>> userRepositoryBranchMap = githubClient.getRepositoryBranchesMap(userRepositoryWithoutForkList);
        return userMapper.mapToUserInfoDto(userRepositoryWithoutForkList, userRepositoryBranchMap);
    }

    private List<GitHubRepositoryDto> removeForkFromUserRepos(List<GitHubRepositoryDto> userRepos) {
        return userRepos.stream()
          .filter(gitHubRepositoryDto -> !gitHubRepositoryDto.isFork())
          .peek(userRepo -> {
              String branchUrl = userRepo.getBranches_url().replace("{/branch}", "");
              userRepo.setBranches_url(branchUrl);
          })
          .collect(Collectors.toList());
    }
}