package com.models;

import lombok.Getter;

@Getter
public enum Locale {
    EN( "English", "en_locale");

    private final String name;
    private final String file;

    Locale(String name, String file) {
        this.name = name;
        this.file = file;
    }
}
