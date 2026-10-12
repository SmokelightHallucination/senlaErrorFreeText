package com.senla.test.senlaerrorfreetext.utils;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TextChunkUtilsTest {

    @Test
    void shouldKeepShortText() {
        String text = "Превет мир";

        assertEquals(List.of(text), TextChunkUtils.splitText(text));
    }

    @Test
    void shouldSplitLongText() {
        String text = "а".repeat(23_000);

        List<String> chunks = TextChunkUtils.splitText(text);

        assertEquals(3, chunks.size());
        assertTrue(chunks.stream()
                .allMatch(chunk -> chunk.length() <= 10_000));
        assertEquals(text, String.join("", chunks));
    }

    @Test
    void shouldSplitAtSpace() {
        String text = "а".repeat(9_995) + " " + "б".repeat(20);

        List<String> chunks = TextChunkUtils.splitText(text);

        assertEquals(2, chunks.size());
        assertEquals(9_996, chunks.get(0).length());
        assertEquals(text, String.join("", chunks));
    }
}
