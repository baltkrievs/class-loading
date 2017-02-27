package by.darashchonak.mentoring.lang.ru;

import java.io.IOException;

import by.darashchonak.mentoring.langs.Language;

public class LangRU extends Language {

    public LangRU() throws IOException, IllegalAccessException {
        super();
    }

    @Override
    public String getLanguageName() {
        return "Русский";
    }

    @Override
    public String getLocaleName() {
        return "ru";
    }
}
