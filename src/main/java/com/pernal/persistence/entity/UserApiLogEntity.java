package com.pernal.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
public class UserApiLogEntity {

    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "request_count")
    private Integer requestCount;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserApiLogEntity that = (UserApiLogEntity) o;
        return Objects.equals(login, that.login) && Objects.equals(requestCount, that.requestCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, requestCount);
    }
}
