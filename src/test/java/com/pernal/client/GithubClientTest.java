package com.pernal.client;

import com.pernal.model.GithubUser;
import com.pernal.model.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GithubClientTest {

    @Autowired
    private GithubClient githubClient;

    @Test
    public void shouldGetUserByLogin(){
        GithubUser mojombo = assertDoesNotThrow(() -> githubClient.getUserByLogin("mojombo"));

        assertNotNull(mojombo);
    }

    @Test
    public void shouldThrowExceptionWhileGetUserByLogin(){
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> githubClient.getUserByLogin("mojombo12"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void shouldGetUserFollowers(){
        List<GithubUser> mojomboFollowers = assertDoesNotThrow(() -> githubClient.getUserFollowers("https://api.github.com/users/mojombo/followers"));

        assertNotNull(mojomboFollowers);
    }

    @Test
    public void shouldThrowExceptionWhileGetUserFollowers(){
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> githubClient.getUserFollowers("https://api.github.com/users/mojombo12/followers"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void shouldGetUserRepos(){
        List<UserRepo> mojomboRepos = assertDoesNotThrow(() -> githubClient.getUserRepos("https://api.github.com/users/mojombo/repos"));

        assertNotNull(mojomboRepos);
    }

    @Test
    public void shouldThrowExceptionWhileGetUserRepos(){
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> githubClient.getUserRepos("https://api.github.com/users/mojombo12/repos"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
