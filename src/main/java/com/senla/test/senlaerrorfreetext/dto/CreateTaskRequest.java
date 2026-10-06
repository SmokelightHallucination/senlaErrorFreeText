package com.senla.test.senlaerrorfreetext.dto;

import com.senla.test.senlaerrorfreetext.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(

        @NotBlank(message = "Text must not be blank")
        @Size(min = 3, message = "Text must contain at least 3 characters")
        String text,

        @NotNull(message = "Language must be specified")
        Language language
) {
}