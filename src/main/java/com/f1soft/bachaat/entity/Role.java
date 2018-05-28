package com.f1soft.bachaat.entity;

import javax.persistence.*;

@Entity
@Table(name = "tbl_roles")
public class Role
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, updatable = false)
    private Long roleId;
    @Column(name = "name")
    private String name;

    public Role()
    {
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Role{" + "roleId=" + roleId + ", name='" + name + '\'' + '}';
    }
}
