package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.dto.response.MenuResponseDTO;
import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.exception.MenuAlreadyExistsException;
import com.f1soft.bachaat.repository.MenuRepository;
import com.f1soft.bachaat.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.f1soft.bachaat.utils.MessageConstant.*;

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
            throw new MenuAlreadyExistsException(MENU_ALREADY_EXISTS);
        }
        return menuRepository.save(menu);
    }

    @Override
    public MenuResponseDTO getMenuById(long id) {
        logger.info(String.format("Menu Service: getMenuById(): with id: %d", id));
        Menu menu = menuRepository.getById(id);
        if (menu == null) {
            throw new DataNotFoundException(String.format(MENU_NOT_FOUND, id));
        }
        MenuResponseDTO parentMenuResponseDTO = new MenuResponseDTO(menu);
        createNode(parentMenuResponseDTO);
        return parentMenuResponseDTO;
    }

    private void createNode(MenuResponseDTO parentMenuResponseDTO) {
        List<MenuResponseDTO> childList = getMenuByParentId(parentMenuResponseDTO.getId());
        childList.forEach(child -> {
            parentMenuResponseDTO.getChildren().add(child);
            createNode(child);
        });
    }

    @Override
    public List<MenuResponseDTO> getMenuByParentId(long id) {
        logger.info(String.format("Menu Service: getByParentId(): with id: %d", id));
        List<Menu> menus = menuRepository.getByParentId(id);
        if (menus == null) {
            throw new DataNotFoundException(String.format(MENU_PARENT_ID_NOT_FOUND, id));
        }
        List<MenuResponseDTO> menuResponseDTOS = new ArrayList<>();
        menus.forEach(menu -> menuResponseDTOS.add(new MenuResponseDTO(menu)));
        return menuResponseDTOS;
    }

    @Override
    public List<MenuResponseDTO> getAll() {
        logger.info(String.format("Menu Service: getAll(): START"));
        List<MenuResponseDTO> menus = getMenuByParentId(0);
        menus.forEach(menu -> createNode(menu));
        return menus;
    }
}
