package com.senla.test.senlaerrorfreetext.utils;

import com.senla.test.senlaerrorfreetext.dto.SpellerError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextCorrectionUtilsTest {

    @Test
    void shouldApplyCorrection() {
        String text = "Превет мир";

        SpellerError error = new SpellerError(
                1, 0, 0, 0, 6, "Превет", List.of("Привет")
        );

        String result = TextCorrectionUtils.applyCorrections(
                text,
                List.of(error)
        );

        assertEquals("Привет мир", result);
    }

    @Test
    void shouldKeepTextWithoutErrors() {
        String text = "Привет мир";

        String result = TextCorrectionUtils.applyCorrections(
                text,
                List.of()
        );

        assertEquals(text, result);
    }

    @Test
    void shouldSkipErrorsWithoutSuggestions() {
        String text = "Превет мир";

        SpellerError error = new SpellerError(
                1, 0, 0, 0, 6, "Превет", List.of()
        );

        String result = TextCorrectionUtils.applyCorrections(
                text,
                List.of(error)
        );

        assertEquals(text, result);
    }

    @Test
    void shouldApplyMultipleCorrectionsFromRightToLeft() {
        String text = "Превет крассивый мир";

        SpellerError first = new SpellerError(
                1, 0, 0, 0, 6, "Превет", List.of("Привет")
        );

        SpellerError second = new SpellerError(
                1, 7, 0, 7, 9, "крассивый", List.of("красивый")
        );

        String result = TextCorrectionUtils.applyCorrections(
                text,
                List.of(first, second)
        );

        assertEquals("Привет красивый мир", result);
    }
}