package com.iotiq.interview.controller.messages;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRequest {
    @NotEmpty
    private String name;

    @NotNull
    UUID menuId;
}
