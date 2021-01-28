package com.pernal.persistence.repository;

import com.pernal.persistence.entity.UserApiLogEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    public void shouldPerformCRUDActions(){
        String login1 = "login1";

        UserApiLogEntity userApiLogEntity = new UserApiLogEntity();
        userApiLogEntity.setRequestCount(1);
        userApiLogEntity.setLogin(login1);

        assertDoesNotThrow(() -> userRepository.save(userApiLogEntity));

        Optional<UserApiLogEntity> optionalRetrievedEntityForLogin1 = userRepository.findByLogin(login1);
        assertTrue(optionalRetrievedEntityForLogin1.isPresent());

        UserApiLogEntity retrievedUserApiLogEntityForLogin1 = optionalRetrievedEntityForLogin1.get();
        assertEquals(1, retrievedUserApiLogEntityForLogin1.getRequestCount());

        Optional<UserApiLogEntity> optionalUpdatedEntityForLogin1 = userRepository.incrementRequestByLogin(login1);
        assertTrue(optionalUpdatedEntityForLogin1.isPresent());

        UserApiLogEntity updatedUserApiLogEntityForLogin1 = optionalUpdatedEntityForLogin1.get();
        assertEquals(2, updatedUserApiLogEntityForLogin1.getRequestCount());



        Optional<UserApiLogEntity> optionalRetrievedEntityForLogin2 = userRepository.findByLogin("login2");
        assertFalse(optionalRetrievedEntityForLogin2.isPresent());

        Optional<UserApiLogEntity> optionalUpdatedEntityForLogin2 = userRepository.incrementRequestByLogin("login2");
        assertFalse(optionalUpdatedEntityForLogin2.isPresent());
    }
}





















