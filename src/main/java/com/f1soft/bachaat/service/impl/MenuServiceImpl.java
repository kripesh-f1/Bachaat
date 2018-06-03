package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.dto.response.MenuDTO;
import com.f1soft.bachaat.repository.MenuRepository;
import com.f1soft.bachaat.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        logger.info(String.format("Menu Service: getMenuById(): with id: %d",id));
        Menu menu = menuRepository.getById(id);
        MenuDTO parentDto = new MenuDTO(menu);
        createNode(parentDto);
        return parentDto;
    }

    private void createNode(MenuDTO parentMenuDTO) {
        List<MenuDTO> childList = getByParentId(parentMenuDTO.getId());
        for (MenuDTO child : childList) {
            parentMenuDTO.addChild(child);
            createNode(child);
        }
    }

    @Override
    public List<MenuDTO> getByParentId(long id) {
        logger.info(String.format("Menu Service: getByParentId(): with id: %d",id));
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
