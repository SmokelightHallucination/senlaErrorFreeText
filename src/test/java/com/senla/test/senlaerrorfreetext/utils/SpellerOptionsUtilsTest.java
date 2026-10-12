package com.senla.test.senlaerrorfreetext.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SpellerOptionsUtilsTest {

    @Test
    void shouldReturnZeroForPlainText() {
        assertEquals(0,
                SpellerOptionsUtils.calculateOptions("Превет мир"));
    }

    @Test
    void shouldIgnoreDigits() {
        assertEquals(2,
                SpellerOptionsUtils.calculateOptions("Превет 123"));
    }

    @Test
    void shouldIgnoreUrls() {
        assertEquals(4,
                SpellerOptionsUtils.calculateOptions(
                        "Посмотри https://example.com"));
    }

    @Test
    void shouldCombineOptions() {
        assertEquals(6,
                SpellerOptionsUtils.calculateOptions(
                        "Посмотри https://example.com 123"));
    }

    @Test
    void shouldDetectWwwUrls() {
        assertEquals(4,
                SpellerOptionsUtils.calculateOptions(
                        "Открой www.example.com"));
    }
}