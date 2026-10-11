package com.senla.test.senlaerrorfreetext.utils;

import java.util.ArrayList;
import java.util.List;

public final class TextChunkUtils {

    private static final int MAX_CHUNK_LENGTH = 10_000;

    private TextChunkUtils() {
    }

    public static List<String> splitText(String text) {
        List<String> chunks = new ArrayList<>();
        int start = 0;

        while (start < text.length()) {
            int end = Math.min(start + MAX_CHUNK_LENGTH, text.length());

            if (end < text.length()) {
                int lastSpace = text.lastIndexOf(' ', end - 1);

                if (lastSpace >= start) {
                    end = lastSpace + 1;
                }
            }

            chunks.add(text.substring(start, end));
            start = end;
        }

        return chunks;
    }
}