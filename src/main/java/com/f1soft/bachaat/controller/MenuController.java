package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.dto.response.MenuResponseDTO;
import com.f1soft.bachaat.responseMessage.ApiMessageResponse;
import com.f1soft.bachaat.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

import static com.f1soft.bachaat.utils.ApiConstant.*;
import static com.f1soft.bachaat.utils.MessageConstant.MENU_ADD_MESSAGE;

@RestController
@RequestMapping(API_VER + MENUS_PATH)
public class MenuController {

    private static Logger logger = Logger.getLogger(MenuController.class.getName());

    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<ApiMessageResponse> addMenu(@RequestBody @Valid Menu menu) {
        logger.info("Menu Controller: addMenu(): START");
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        menuService.addMenu(menu);
        apiMessageResponse.setMessage(MENU_ADD_MESSAGE);
        return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> getMenus() {
        logger.info("Menu Controller: getMenu(): START");
        List<MenuResponseDTO> menuResponseDTOList = menuService.getAll();
        return new ResponseEntity<>(menuResponseDTOList, HttpStatus.OK);
    }

    @GetMapping(MENU_PATH)
    public ResponseEntity<MenuResponseDTO> getMenuById(@RequestParam long id) {
        logger.info(String.format("Menu Controller: getMenuById(): with id: %d", id));
        MenuResponseDTO menu = menuService.getMenuById(id);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
}
