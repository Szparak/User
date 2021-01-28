package com.pernal.service;

import com.pernal.model.UserRsp;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<UserRsp> get(String login);
}
