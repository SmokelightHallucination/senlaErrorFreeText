package com.senla.test.senlaerrorfreetext.dto;

import java.util.List;

public record SpellerError(
        int code,
        int pos,
        int row,
        int col,
        int len,
        String word,
        List<String> s
) {
}
