package com.senla.test.senlaerrorfreetext.utils;

import com.senla.test.senlaerrorfreetext.dto.SpellerError;
import java.util.Comparator;
import java.util.List;

public final class TextCorrectionUtils {
    private TextCorrectionUtils() {
    }

    public static String applyCorrections(
            String text,
            List<SpellerError> errors
    ) {
        StringBuilder result = new StringBuilder(text);

        errors.stream()
                .filter(error -> error.s() != null && !error.s().isEmpty())
                .sorted(Comparator.comparingInt(SpellerError::pos).reversed())
                .forEach(error -> result.replace(
                        error.pos(),
                        error.pos() + error.len(),
                        error.s().get(0)
                ));

        return result.toString();
    }
}
