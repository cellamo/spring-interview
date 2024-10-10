package com.iotiq.interview.service;

import com.iotiq.interview.controller.messages.MenuRequest;
import com.iotiq.interview.domain.Menu;
import com.iotiq.interview.exception.DuplicateMenuNameException;
import com.iotiq.interview.exception.MenuNotFoundException;
import com.iotiq.interview.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMenu() {
        // Arrange
        MenuRequest request = new MenuRequest();
        request.setName("Test Menu");

        Menu menu = new Menu();
        menu.setName("Test Menu");
        setEntityId(menu, UUID.randomUUID());

        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        // Act
        Menu result = menuService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals("Test Menu", result.getName());
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    void testGetAllMenus() {
        // Arrange
        Menu menu1 = new Menu();
        menu1.setName("Menu 1");
        setEntityId(menu1, UUID.randomUUID());

        Menu menu2 = new Menu();
        menu2.setName("Menu 2");
        setEntityId(menu2, UUID.randomUUID());

        List<Menu> menus = Arrays.asList(menu1, menu2);
        when(menuRepository.findAll()).thenReturn(menus);

        // Act
        List<Menu> result = menuService.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(menuRepository, times(1)).findAll();
    }

    @Test
    void testGetById_MenuNotFound() {
        // Arrange
        UUID menuId = UUID.randomUUID();
        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MenuNotFoundException.class, () -> menuService.getById(menuId));
        verify(menuRepository, times(1)).findById(menuId);
    }

    private void setEntityId(AbstractPersistable<UUID> entity, UUID id) {
        try {
            Field idField = AbstractPersistable.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetFilteredMenus() {
        // Arrange
        Menu menu1 = new Menu();
        menu1.setName("Italian Menu");
        Menu menu2 = new Menu();
        menu2.setName("French Menu");

        when(menuRepository.findAllByNameContainingIgnoreCase("Italian")).thenReturn(Collections.singletonList(menu1));

        // Act
        List<Menu> result = menuService.getFiltered("Italian");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Italian Menu", result.get(0).getName());
        verify(menuRepository, times(1)).findAllByNameContainingIgnoreCase("Italian");
    }

    @Test
    void testCreateMenu_DuplicateName() {
        // Arrange
        MenuRequest request = new MenuRequest();
        request.setName("Existing Menu");

        when(menuRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateMenuNameException.class, () -> menuService.create(request));
        verify(menuRepository, times(1)).existsByNameIgnoreCase("Existing Menu");
        verify(menuRepository, never()).save(any(Menu.class));
    }

    @Test
    void testUpdateMenu_DuplicateName() {
        // Arrange
        UUID menuId = UUID.randomUUID();
        MenuRequest request = new MenuRequest();
        request.setName("New Name");

        Menu existingMenu = new Menu();
        existingMenu.setName("Old Name");
        setEntityId(existingMenu, menuId);

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(existingMenu));
        when(menuRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateMenuNameException.class, () -> menuService.update(menuId, request));
        verify(menuRepository, times(1)).findById(menuId);
        verify(menuRepository, times(1)).existsByNameIgnoreCase("New Name");
        verify(menuRepository, never()).save(any(Menu.class));
    }
}
