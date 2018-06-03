package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.dto.response.MenuDTO;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.exception.UserAlreadyExistsException;
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
        Menu currentMenu = menuRepository.findByNameAndLinkAndParentId(menu.getName(), menu.getLink(), menu.getParentId());
        if (currentMenu != null) {
            throw new UserAlreadyExistsException("Given menu already exists!");
        }
        return menuRepository.save(menu);
    }

    @Override
    public MenuDTO getMenuById(long id) {
        logger.info(String.format("Menu Service: getMenuById(): with id: %d", id));
        Menu menu = menuRepository.getById(id);
        if(){

        }
        try {
            Menu menu = menuRepository.getById(id);
            MenuDTO parentDto = new MenuDTO(menu);
            createNode(parentDto);
            return parentDto;
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(String.format("Menu not found with id: %d", id));
        }
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
        logger.info(String.format("Menu Service: getByParentId(): with id: %d", id));
        try {
            List<Menu> byParentId = menuRepository.getByParentId(id);
            List<MenuDTO> menuDTOS = new ArrayList<>(byParentId.size());
            for (Menu menu : byParentId) {
                menuDTOS.add(new MenuDTO(menu));
            }
            return menuDTOS;
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(String.format("Menu not found with id: %d", id));
        }
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
