package com.pernal.persistence.repository;

import com.pernal.persistence.entity.UserApiLogEntity;
import org.hibernate.HibernateException;

import java.util.Optional;

public interface IUserRepository {

    UserApiLogEntity save(UserApiLogEntity userApiLogEntity) throws HibernateException;

    Optional<UserApiLogEntity> findByLogin(String login) throws HibernateException;

    Optional<UserApiLogEntity> incrementRequestByLogin(String login) throws HibernateException;
}
