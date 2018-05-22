package com.kat.bachaat.model;

import java.util.List;

public class Authority {
    private int authorityId;
    private String name;
    private List<User> userList;

    public Authority() {
    }

    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "authorityId=" + authorityId +
                ", name='" + name + '\'' +
                ", userList=" + userList +
                '}';
    }
}
