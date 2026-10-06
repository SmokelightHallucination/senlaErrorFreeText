package com.senla.test.senlaerrorfreetext.dto;

import com.senla.test.senlaerrorfreetext.enums.TaskStatus;

public record TaskResponse(
        TaskStatus status,
        String correctedText,
        String errorMessage
) {
}