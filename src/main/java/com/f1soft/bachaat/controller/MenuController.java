package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.entity.Menu;
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

@RestController
@RequestMapping(API_VER + MENU_PATH)
public class MenuController {

    private static Logger logger = Logger.getLogger(MenuController.class.getName());

    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<ApiMessageResponse> addMenu(@RequestBody @Valid Menu menu) {
        logger.info("Menu Controller: addMenu(): START");
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        menuService.addMenu(menu);
        apiMessageResponse.setMessage("Menu has been added successfully.");
        return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getMenu() {
        List<Menu> menuList = menuService.getMenu();
        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }

}
