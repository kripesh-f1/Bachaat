package com.kat.bachaat.model;

public class Role {
    private int roleId;
    private String name;

    public Role() {
    }

    public int getAuthorityId() {
        return roleId;
    }

    public void setAuthorityId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}
