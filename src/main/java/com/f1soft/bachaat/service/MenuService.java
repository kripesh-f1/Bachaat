package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.Menu;

import java.util.List;

public interface MenuService {
    Menu addMenu(Menu menu);
    List<Menu> getMenu();
}
