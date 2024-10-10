package com.iotiq.interview.service;

import com.iotiq.interview.controller.messages.MenuRequest;
import com.iotiq.interview.domain.Menu;
import com.iotiq.interview.exception.MenuNotFoundException;
import com.iotiq.interview.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Menu getById(UUID id) {
        Optional<Menu> byId = menuRepository.findById(id);
        if(byId.isPresent()) {
            return byId.get();
        } else {
            throw new MenuNotFoundException();
        }
    }
    public List<Menu> getFiltered(String name) {
        if (name != null && !name.isEmpty()) {
            return menuRepository.findAllByNameContainingIgnoreCase(name);
        }
        return menuRepository.findAll();
    }
    
}
