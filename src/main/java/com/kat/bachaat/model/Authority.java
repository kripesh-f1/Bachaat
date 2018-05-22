package com.kat.bachaat.model;

import javax.persistence.*;

@Entity
@Table(name="tbl_authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id", updatable = false, nullable = false)
    private int authorityId;
    @Column(name="authority_name")
    private String name;

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

    @Override
    public String toString() {
        return "Authority{" +
                "authorityId=" + authorityId +
                ", name='" + name + '\'' +
                '}';
    }
}
