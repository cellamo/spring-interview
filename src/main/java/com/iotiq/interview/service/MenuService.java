package com.iotiq.interview.service;

import com.iotiq.interview.controller.messages.MenuRequest;
import com.iotiq.interview.domain.Menu;
import com.iotiq.interview.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public Menu create(MenuRequest request) {
        Menu menu = new Menu();
        menu.setName(request.getName());
        return menuRepository.save(menu);
    }

    public List<Menu> getAll() {
        return menuRepository.findAll();
    }
}
