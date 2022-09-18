package com.atipera.recruitment.controller;

import com.atipera.recruitment.model.RepositoryDto;
import com.atipera.recruitment.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{login}")
    public List<RepositoryDto> getUserInfo(@PathVariable String login) {
        return service.getUserInfo(login);
    }
}
