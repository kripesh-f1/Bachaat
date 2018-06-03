package com.f1soft.bachaat.dto.response;

import com.f1soft.bachaat.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuDTO {
    private long id;
    private String name;
    private String link;
    private long parentId;
    private boolean enable;
    private List<MenuDTO> children = new ArrayList<>();

    public MenuDTO(Menu menu) {
        this.name = menu.getName();
        this.link = menu.getLink();
        this.id = menu.getId();
        this.parentId = menu.getParentId();
        this.enable = menu.isEnable();
    }

    public void addChild(MenuDTO menuDTO) {
        children.add(menuDTO);
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }
}
