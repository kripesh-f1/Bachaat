package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.dto.response.MenuResponseDTO;

import java.util.List;

public interface MenuService {
    Menu addMenu(Menu menu);

    MenuResponseDTO getMenuById(long id);

    List<MenuResponseDTO> getMenuByParentId(long id);

    List<MenuResponseDTO> getAll();
}
