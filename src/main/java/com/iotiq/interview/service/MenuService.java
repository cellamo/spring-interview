package com.iotiq.interview.service;

import com.iotiq.interview.controller.messages.MenuRequest;
import com.iotiq.interview.domain.Menu;
import com.iotiq.interview.exception.DuplicateMenuNameException;
import com.iotiq.interview.exception.MenuNotFoundException;
import com.iotiq.interview.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public Menu create(MenuRequest request) {
        if (menuRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateMenuNameException();
        }
        Menu menu = new Menu();
        menu.setName(request.getName());
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu update(UUID id, MenuRequest request) {
        Menu menu = getById(id);
        if (!menu.getName().equalsIgnoreCase(request.getName()) &&
                menuRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateMenuNameException();
        }
        menu.setName(request.getName());
        return menuRepository.save(menu);
    }

    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    public Menu getById(UUID id) {
        Optional<Menu> byId = menuRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new MenuNotFoundException();
        }
    }

    public Page<Menu> getFiltered(String name, Pageable pageable) {
        if (name != null && !name.isEmpty()) {
            return menuRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }
        return menuRepository.findAll(pageable);
    }
}