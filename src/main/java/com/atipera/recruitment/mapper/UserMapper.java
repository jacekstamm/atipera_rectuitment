package com.atipera.recruitment.mapper;

import com.atipera.recruitment.model.RepositoryDto;
import com.atipera.recruitment.model.github.Branch;
import com.atipera.recruitment.model.github.GitHubBranchDto;
import com.atipera.recruitment.model.github.GitHubRepositoryDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public List<RepositoryDto> mapToUserInfoDto(List<GitHubRepositoryDto> gitHubUserDto,
                                                Map<String, List<GitHubBranchDto>> userRepositoryBranchMap) {
        List<RepositoryDto> repositoryList = mapToRepositoryList(gitHubUserDto);
        return mapToRepositoryWithBranches(gitHubUserDto, userRepositoryBranchMap, repositoryList);
    }

    private List<RepositoryDto> mapToRepositoryList(List<GitHubRepositoryDto> gitHubRepositoryList) {
        List<RepositoryDto> repositoryList = new ArrayList<>();
        for (GitHubRepositoryDto repository : gitHubRepositoryList) {
            repositoryList.add(RepositoryDto.builder()
              .repositoryName(repository.getName())
              .ownerName(repository.getOwner().getLogin())
              .build());
        }
        return repositoryList;
    }

    private List<RepositoryDto> mapToRepositoryWithBranches(List<GitHubRepositoryDto> gitHubRepositoryList,
                                                            Map<String, List<GitHubBranchDto>> gitHubBranchMap,
                                                            List<RepositoryDto> repositoryList) {
        for (RepositoryDto repository : repositoryList) {
            for (GitHubRepositoryDto gitHubRepository : gitHubRepositoryList) {
                String branchUrl = gitHubRepository.getBranches_url();
                String repositoryName = gitHubRepository.getName();
                if (repository.getRepositoryName().equals(repositoryName)) {
                    List<GitHubBranchDto> gitHubBranchList = gitHubBranchMap.get(branchUrl);
                    repository.setBranchList(createBranchList(gitHubBranchList));
                }
            }
        }
        return repositoryList;
    }

    private List<Branch> createBranchList(List<GitHubBranchDto> gitHubBranchList) {
        List<Branch> branchList = new ArrayList<>();
        for (GitHubBranchDto gitHubBranch : gitHubBranchList) {
            branchList.add(Branch.builder()
              .branchName(gitHubBranch.getName())
              .lastCommitSha(gitHubBranch.getCommit().getSha())
              .build());
        }
        return branchList;
    }
}
