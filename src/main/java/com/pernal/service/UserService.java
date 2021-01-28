package com.pernal.service;

import com.pernal.calculator.UserCalculator;
import com.pernal.client.GithubClient;
import com.pernal.model.GithubUser;
import com.pernal.model.UserRepo;
import com.pernal.model.UserRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class UserService implements IUserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private final GithubClient githubClient;

    public UserService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    @Override
    public ResponseEntity<UserRsp> get(String login) {
        try {
            GithubUser userByLogin = githubClient.getUserByLogin(login);
            List<GithubUser> userFollowers = githubClient.getUserFollowers(userByLogin.getFollowersUrl());
            List<UserRepo> userRepos = githubClient.getUserRepos(userByLogin.getReposUrl());

            long publicRepos = userRepos.stream().filter(userRepo -> !userRepo.isPrivate()).count();

            try {
                Double calculations = UserCalculator.calculate(userFollowers.size(), publicRepos);

                return ResponseEntity.ok(createUserRsp(userByLogin, calculations));
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to perform calculations for login: {} - divide by zero", login);
                return ResponseEntity.ok(createUserRsp(userByLogin, null));
            }

        } catch (HttpClientErrorException e) {
            logger.error("Error while calling github api for login: " + login, e);
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            logger.error("Error while getting user data for login: " + login, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    private UserRsp createUserRsp(GithubUser userByLogin, Double calculations) {
        UserRsp userRsp = new UserRsp();

        userRsp.setLogin(userByLogin.getLogin());
        userRsp.setAvatarUrl(userByLogin.getAvatarUrl());
        userRsp.setCalculations(calculations);
        userRsp.setId(userByLogin.getId());
        userRsp.setName(userByLogin.getName());
        userRsp.setCreatedAt(userByLogin.getCreatedAt());
        userRsp.setType(userByLogin.getType());

        return userRsp;
    }
}
