package com.pernal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRepo {
    @JsonProperty("private")
    private boolean isPrivate;

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
