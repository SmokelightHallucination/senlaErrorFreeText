package com.senla.test.senlaerrorfreetext.client;

import com.senla.test.senlaerrorfreetext.dto.SpellerError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class YandexSpellerClientIntegrationTest {

    private final YandexSpellerClient client =
            new YandexSpellerClient(RestClient.builder());

    @Test
    void shouldDetectSpellingErrors() {
        List<SpellerError> errors = client.checkText(
                "Превет мир",
                "ru",
                0
        );

        System.out.println("Yandex response: " + errors);

        assertNotNull(errors);
        assertFalse(errors.isEmpty());

        SpellerError firstError = errors.get(0);

        assertEquals("Превет", firstError.word());
        assertTrue(firstError.s().contains("Привет"));
    }
}
