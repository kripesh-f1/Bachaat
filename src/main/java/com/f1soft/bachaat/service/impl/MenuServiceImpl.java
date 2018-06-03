package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.exception.MenuAlreadyExistsException;
import com.f1soft.bachaat.exception.UserAlreadyExistsException;
import com.f1soft.bachaat.repository.MenuRepository;
import com.f1soft.bachaat.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
