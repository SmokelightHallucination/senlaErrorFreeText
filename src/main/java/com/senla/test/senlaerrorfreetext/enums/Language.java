package com.senla.test.senlaerrorfreetext.enums;

import lombok.Getter;

@Getter
public enum Language {
    RU("ru"),
    EN("en");

    private final String apiValue;

    Language(String apiValue) {
        this.apiValue = apiValue;
    }

}
