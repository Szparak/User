package com.pernal.persistence.repository;

import com.pernal.persistence.entity.UserApiLogEntity;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Transactional
public class UserRepository implements IUserRepository {

    private Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserApiLogEntity save(UserApiLogEntity userApiLogEntity) throws HibernateException {
        logger.info("Save UserApiLogEntity for login: {} started...", userApiLogEntity.getLogin());

        entityManager.persist(userApiLogEntity);

        logger.info("Save UserApiLogEntity for login: {} finished...", userApiLogEntity.getLogin());

        return userApiLogEntity;
    }

    public Optional<UserApiLogEntity> findByLogin(String login) throws HibernateException {
        logger.info("Get log for login: {} started...", login);

        UserApiLogEntity userApiLogEntity = entityManager.find(UserApiLogEntity.class, login);

        logger.info("Get log for login: {} finished...", login);

        return Optional.ofNullable(userApiLogEntity);
    }

    @Override
    public Optional<UserApiLogEntity> incrementRequestByLogin(String login) throws HibernateException {
        logger.info("Update log for login: {} started...", login);

        Optional<UserApiLogEntity> userEntityOpt = findByLogin(login);
        if(userEntityOpt.isPresent()){
            UserApiLogEntity userApiLogEntity = userEntityOpt.get();
            userApiLogEntity.setRequestCount(userApiLogEntity.getRequestCount() + 1);
            entityManager.merge(userApiLogEntity);

            logger.info("Update log for login: {} finished...", login);

            return Optional.of(userApiLogEntity);
        } else {
            logger.info("Update log for login: {} failed - entity not found", login);
            return Optional.empty();
        }
    }
}
