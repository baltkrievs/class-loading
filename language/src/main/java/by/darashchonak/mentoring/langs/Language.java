package by.darashchonak.mentoring.langs;

import java.io.IOException;
import java.util.Properties;

public abstract class Language {

    private Properties props = new Properties();

    public Language() throws IOException, IllegalAccessException {
        loadProps();
    }

    public abstract String getLanguageName();

    public abstract String getLocaleName();

    public String getProperty(String key) {
        return props.getProperty(key);
    }

    private void loadProps() throws IOException {
        this.props.load(
                this.getClass().getClassLoader().getResourceAsStream("lang_" + this.getLocaleName() + ".properties"));
    }
}
