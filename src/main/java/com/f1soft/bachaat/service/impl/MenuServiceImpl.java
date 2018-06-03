package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.entity.MenuDTO;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.repository.MenuRepository;
import com.f1soft.bachaat.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    private static Logger logger = Logger.getLogger(MenuServiceImpl.class.getName());

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Menu addMenu(Menu menu) {
        logger.info("Menu Service: addMenu(): START");
        return menuRepository.save(menu);
    }

    @Override
    public MenuDTO getMenuById(long id) {
        Menu menuById = menuRepository.getById(id);
        MenuDTO parentDto = new MenuDTO(menuById);
        createNode(parentDto);
        return parentDto;
    }

    private void createNode(MenuDTO parent) {
        List<MenuDTO> child = getByParentId(parent.getId());
        for (MenuDTO c : child) {
            parent.addChild(c);
            createNode(c);
        }
    }

    @Override
    public List<MenuDTO> getByParentId(long id) {
        List<Menu> byParentId = menuRepository.getByParentId(id);
        List<MenuDTO> menuDTOS = new ArrayList<>(byParentId.size());
        for (Menu menu : byParentId) {
            menuDTOS.add(new MenuDTO(menu));
        }
        return menuDTOS;
    }

    @Override
    public List<MenuDTO> getAll() {
        List<MenuDTO> byParentId = getByParentId(0);
        for (int i = 0; i < byParentId.size(); i++) {
            createNode(byParentId.get(i));
        }
        return byParentId;
    }
}
