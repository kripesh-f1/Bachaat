package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.responseMessage.ApiMessageResponse;
import com.f1soft.bachaat.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
        logger.info("Inside Menu Controller: addMenu()");
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        menuService.addMenu(menu);
        apiMessageResponse.setMessage("Menu has been added successfully.");
        return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
    }

}
