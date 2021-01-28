package com.pernal.service;

import com.pernal.client.GithubClient;
import com.pernal.model.GithubUser;
import com.pernal.model.UserRepo;
import com.pernal.model.UserRsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private GithubClient githubClient;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetUserData(){
        GithubUser githubUser = createGithubUser();
        given(githubClient.getUserByLogin("mojombo")).willReturn(githubUser);
        given(githubClient.getUserFollowers("https://api.github.com/users/mojombo/followers")).willReturn(Collections.singletonList(new GithubUser()));
        given(githubClient.getUserRepos("https://api.github.com/users/mojombo/repos")).willReturn(Collections.singletonList(new UserRepo()));

        ResponseEntity<UserRsp> userRspResponseEntity = userService.get("mojombo");
        UserRsp userRsp = userRspResponseEntity.getBody();

        assertEquals(200, userRspResponseEntity.getStatusCodeValue());
        assertNotNull(userRsp);
        assertEquals(githubUser.getCreatedAt(), userRsp.getCreatedAt());
        assertEquals(githubUser.getAvatarUrl(), userRsp.getAvatarUrl());
        assertEquals(githubUser.getId(), userRsp.getId());
        assertEquals(githubUser.getLogin(), userRsp.getLogin());
        assertEquals(githubUser.getName(), userRsp.getName());
        assertEquals(githubUser.getType(), userRsp.getType());
        assertEquals(18, userRsp.getCalculations());

        verify(githubClient, times(1)).getUserByLogin("mojombo");
        verify(githubClient, times(1)).getUserFollowers("https://api.github.com/users/mojombo/followers");
        verify(githubClient, times(1)).getUserRepos("https://api.github.com/users/mojombo/repos");
    }

    @Test
    public void shouldGetUserDataWithCalculationsError(){
        GithubUser githubUser = createGithubUser();
        given(githubClient.getUserByLogin("mojombo")).willReturn(githubUser);
        given(githubClient.getUserFollowers("https://api.github.com/users/mojombo/followers")).willReturn(Collections.emptyList());
        given(githubClient.getUserRepos("https://api.github.com/users/mojombo/repos")).willReturn(Collections.singletonList(new UserRepo()));

        ResponseEntity<UserRsp> userRspResponseEntity = userService.get("mojombo");
        UserRsp userRsp = userRspResponseEntity.getBody();

        assertEquals(200, userRspResponseEntity.getStatusCodeValue());
        assertNotNull(userRsp);
        assertEquals(githubUser.getCreatedAt(), userRsp.getCreatedAt());
        assertEquals(githubUser.getAvatarUrl(), userRsp.getAvatarUrl());
        assertEquals(githubUser.getId(), userRsp.getId());
        assertEquals(githubUser.getLogin(), userRsp.getLogin());
        assertEquals(githubUser.getName(), userRsp.getName());
        assertEquals(githubUser.getType(), userRsp.getType());
        assertNull(userRsp.getCalculations());

        verify(githubClient, times(1)).getUserByLogin("mojombo");
        verify(githubClient, times(1)).getUserFollowers("https://api.github.com/users/mojombo/followers");
        verify(githubClient, times(1)).getUserRepos("https://api.github.com/users/mojombo/repos");
    }

    @Test
    public void shouldGetUserDataWithHttpClientErrorException(){
        given(githubClient.getUserByLogin("mojombo")).willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        ResponseEntity<UserRsp> userRspResponseEntity = userService.get("mojombo");
        UserRsp userRsp = userRspResponseEntity.getBody();

        assertNull(userRsp);
        assertEquals(HttpStatus.NOT_FOUND, userRspResponseEntity.getStatusCode());

        verify(githubClient, times(1)).getUserByLogin("mojombo");
        verify(githubClient, times(0)).getUserFollowers(any());
        verify(githubClient, times(0)).getUserRepos(any());
    }

    private GithubUser createGithubUser() {
        GithubUser githubUser = new GithubUser();

        githubUser.setCreatedAt("2007-10-20T05:24:19Z");
        githubUser.setAvatarUrl("https://avatars.githubusercontent.com/u/1?v=4");
        githubUser.setId(1);
        githubUser.setLogin("mojombo");
        githubUser.setFollowersUrl("https://api.github.com/users/mojombo/followers");
        githubUser.setReposUrl("https://api.github.com/users/mojombo/repos");
        githubUser.setType("User");

        return githubUser;
    }
}
