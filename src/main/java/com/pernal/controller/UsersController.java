package com.pernal.controller;

import com.pernal.model.UserRsp;
import com.pernal.service.IUserService;
import com.pernal.util.UserApiActivityLogger;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

    private final IUserService userService;
    private final UserApiActivityLogger userApiActivityLogger;

    public UsersController(IUserService userService, UserApiActivityLogger userApiActivityLogger) {
        this.userService = userService;
        this.userApiActivityLogger = userApiActivityLogger;
    }

    @ApiOperation(value = "Get user by login", notes = "This method is used to get user by provided login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting user processed"),
            @ApiResponse(code = 500, message = "Error while getting user")})
    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRsp> get(@PathVariable String login) {
        userApiActivityLogger.logGet(login);
        return userService.get(login);
    }
}
