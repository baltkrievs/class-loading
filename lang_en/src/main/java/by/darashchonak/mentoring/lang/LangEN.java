package by.darashchonak.mentoring.lang;

import java.io.IOException;

import by.darashchonak.mentoring.langs.Language;

public class LangEN extends Language {

    public LangEN() throws IOException, IllegalAccessException {
        super();
    }

    @Override
    public String getLanguageName() {
        return "English";
    }

    @Override
    public String getLocaleName() {
        return "en";
    }
}
