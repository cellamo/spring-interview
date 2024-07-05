package com.iotiq.interview.controller.messages;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateResponse {
    private UUID id;
}
