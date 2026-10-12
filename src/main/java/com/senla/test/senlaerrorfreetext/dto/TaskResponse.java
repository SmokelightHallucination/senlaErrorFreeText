package com.senla.test.senlaerrorfreetext.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senla.test.senlaerrorfreetext.enums.TaskStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskResponse(
        TaskStatus status,
        String correctedText,
        String errorMessage
) {
}