package com.senla.test.senlaerrorfreetext.service;

import com.senla.test.senlaerrorfreetext.enums.Language;

public interface TextCorrectionService {
    String correctText(String text, Language language);
}
