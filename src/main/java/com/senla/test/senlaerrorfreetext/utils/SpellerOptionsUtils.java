package com.senla.test.senlaerrorfreetext.utils;

public final class SpellerOptionsUtils {

    private static final int IGNORE_DIGITS = 2;
    private static final int IGNORE_URLS = 4;

    private SpellerOptionsUtils() {
    }

    public static int calculateOptions(String text) {
        int options = 0;

        boolean containsDigits = text.codePoints()
                .anyMatch(Character::isDigit);

        if (containsDigits) {
            options |= IGNORE_DIGITS;
        }

        String lowerCaseText = text.toLowerCase(java.util.Locale.ROOT);

        boolean containsUrl = lowerCaseText.contains("http://")
                || lowerCaseText.contains("https://")
                || lowerCaseText.contains("www.");

        if (containsUrl) {
            options |= IGNORE_URLS;
        }

        return options;
    }
}