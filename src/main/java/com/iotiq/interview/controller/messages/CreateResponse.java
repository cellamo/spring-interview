package com.iotiq.interview.controller.messages;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CreateResponse {
    private UUID id;
}
