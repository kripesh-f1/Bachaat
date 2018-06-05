package com.f1soft.bachaat.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.f1soft.bachaat.utils.DTOMessageConstant.*;

@Entity
@Table(name = "tbl_menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private long id;
    @NotNull(message = MENU_NAME_REQUIRED)
    @Column(name = "menu_name")
    private String name;
    @Column(name = "parent_id")
    private long parentId;
    @NotNull(message = LINK_REQUIRED)
    private String link;
    @Column(name = "status")
    private boolean enable = true;

    public Menu() {
    }

    public Menu(long id, String name, long parentId, String link) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", link='" + link + '\'' +
                ", enable=" + enable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (parentId != menu.parentId) return false;
        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;
        return link != null ? link.equals(menu.link) : menu.link == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (int) (parentId ^ (parentId >>> 32));
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
