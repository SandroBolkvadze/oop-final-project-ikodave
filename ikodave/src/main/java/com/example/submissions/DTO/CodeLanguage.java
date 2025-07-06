package com.example.submissions.DTO;

public class CodeLanguage {

    private final int id;
    private final String language;

    public int getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public CodeLanguage(int id, String language) {
        this.id = id;
        this.language = language;
    }
}
