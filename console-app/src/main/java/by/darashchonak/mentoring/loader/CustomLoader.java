package by.darashchonak.mentoring.loader;

import java.net.URL;
import java.net.URLClassLoader;

public class CustomLoader extends URLClassLoader {

    public CustomLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

}
