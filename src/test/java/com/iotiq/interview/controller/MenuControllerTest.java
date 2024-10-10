package com.iotiq.interview.controller;

import com.iotiq.interview.controller.messages.CreateResponse;
import com.iotiq.interview.controller.messages.MenuRequest;
import com.iotiq.interview.controller.messages.MenuResponse;
import com.iotiq.interview.domain.Menu;
import com.iotiq.interview.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        // Arrange
        Menu menu1 = new Menu();
        menu1.setName("Menu 1");
        Menu menu2 = new Menu();
        menu2.setName("Menu 2");
        List<Menu> menus = Arrays.asList(menu1, menu2);
        when(menuService.getFiltered(null)).thenReturn(menus);
    
        // Act
        List<MenuResponse> result = menuController.getAll(null);
    
        // Assert
        assertEquals(2, result.size());
        assertEquals("Menu 1", result.get(0).getName());
        assertEquals("Menu 2", result.get(1).getName());
        verify(menuService, times(1)).getFiltered(null);
    }

    @Test
    void testCreate() {
        // Arrange
        MenuRequest request = new MenuRequest();
        request.setName("New Menu");
        Menu createdMenu = new Menu();
        setEntityId(createdMenu, UUID.randomUUID());
        createdMenu.setName("New Menu");
        when(menuService.create(any(MenuRequest.class))).thenReturn(createdMenu);

        // Act
        CreateResponse response = menuController.create(request);

        // Assert
        assertNotNull(response);
        assertEquals(createdMenu.getId(), response.getId());
        verify(menuService, times(1)).create(any(MenuRequest.class));
    }
    @Test
    void testCreate_InvalidRequest() {
        // Arrange
        MenuRequest request = new MenuRequest();
        // Name is left empty
        when(menuService.create(any(MenuRequest.class))).thenThrow(new IllegalArgumentException("Menu name cannot be null or empty"));
    
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> menuController.create(request));
        assertEquals("Menu name cannot be null or empty", exception.getMessage());
        verify(menuService, times(1)).create(any(MenuRequest.class));
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
}
