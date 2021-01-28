package com.pernal.client;

import com.pernal.model.GithubUser;
import com.pernal.model.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class GithubClient {

    private Logger logger = LoggerFactory.getLogger(GithubClient.class);
    private final String baseUsersUrl = "https://api.github.com/users/";
    private final RestTemplate restTemplate = new RestTemplate();

    public GithubUser getUserByLogin(String login) {
        logger.info("Retrieving user data for login {} started...", login);
        String userUrl = UriComponentsBuilder.fromHttpUrl(baseUsersUrl).pathSegment(login).build().toUriString();

        ResponseEntity<GithubUser> githubUserResponseEntity = restTemplate.exchange(userUrl, HttpMethod.GET, HttpEntity.EMPTY, GithubUser.class);

        return githubUserResponseEntity.getBody();
    }

    public List<GithubUser> getUserFollowers(String url) {
        logger.info("Retrieving user followers for url {} started...", url);

        ResponseEntity<List<GithubUser>> githubUserResponseEntity = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<GithubUser>>() {
        });

        return githubUserResponseEntity.getBody();
    }

    public List<UserRepo> getUserRepos(String url) {
        logger.info("Retrieving user repos for url {} started...", url);

        ResponseEntity<List<UserRepo>> githubUserResponseEntity = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<UserRepo>>() {
        });

        return githubUserResponseEntity.getBody();
    }
}
