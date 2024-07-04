package com.iotiq.interview.controller.messages;

import com.iotiq.interview.domain.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuResponse {

    private String name;
    private List<CategoryResponse> categories;

    public static MenuResponse of(Menu menu) {
        MenuResponse menuResponse = new MenuResponse();

        menuResponse.setName(menu.getName());
        menuResponse.setCategories(menu.getCategories().stream().map(CategoryResponse::of).toList());

        return menuResponse;
    }
}
