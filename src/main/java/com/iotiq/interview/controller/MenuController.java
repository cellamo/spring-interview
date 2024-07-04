package com.iotiq.interview.controller;

import com.iotiq.interview.controller.messages.CreateResponse;
import com.iotiq.interview.controller.messages.MenuRequest;
import com.iotiq.interview.controller.messages.MenuResponse;
import com.iotiq.interview.domain.Menu;
import com.iotiq.interview.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public List<MenuResponse> getAll() {
        return menuService.getAll()
                .stream()
                .map(MenuResponse::of)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateResponse create(@Valid @RequestBody MenuRequest request) {
        Menu menu = menuService.create(request);
        return CreateResponse.builder().id(menu.getId()).build();
    }
}
