package com.iotiq.interview.controller.messages;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MenuRequest {
    @NotEmpty
    String name;
}
