package com.iotiq.interview.controller.messages;

import com.iotiq.interview.domain.Category;
import lombok.Data;

@Data
public class CategoryResponse {

    private String name;

    public static CategoryResponse of(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setName(category.getName());

        return categoryResponse;
    }
}
