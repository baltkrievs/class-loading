package by.darashchonak.mentoring.lang.by;

import java.io.IOException;

import by.darashchonak.mentoring.langs.Language;

public class LangBY extends Language {

    public LangBY() throws IOException, IllegalAccessException {
        super();
    }

    @Override
    public String getLanguageName() {
        return "Беларуская";
    }

    @Override
    public String getLocaleName() {
        return "by";
    }
}
