package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.entity.MenuDTO;

import java.util.List;
import java.util.Map;

public interface MenuService {
    Menu addMenu(Menu menu);

    MenuDTO getMenuById(long id);

    List<MenuDTO> getByParentId(long id);

    List<MenuDTO> getAll();
}
