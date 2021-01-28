package com.pernal.util;

import com.pernal.persistence.entity.UserApiLogEntity;
import com.pernal.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserApiActivityLoggerTest {

    @InjectMocks
    private UserApiActivityLogger userApiActivityLogger;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldLogGetForExistingEntity(){
        given(userRepository.incrementRequestByLogin(any())).willReturn(Optional.of(new UserApiLogEntity()));

        userApiActivityLogger.logGet("login");

        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void shouldLogGetForNotExistingEntity(){
        given(userRepository.incrementRequestByLogin(any())).willReturn(Optional.empty());

        userApiActivityLogger.logGet("login");

        verify(userRepository, times(1)).save(any());
    }
}
