package by.darashchonak.mentoring.app;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import by.darashchonak.mentoring.app.constant.AppConst;
import by.darashchonak.mentoring.lang.LangEN;
import by.darashchonak.mentoring.langs.Language;
import by.darashchonak.mentoring.loader.CustomLoader;

public class App {

    private CustomLoader classLoader;
    // We need at least one default language at startup
    private Language currentLanguage;
    Map<String, Language> availableLangs;

    public App() throws IllegalAccessException, IOException {
        this.classLoader = new CustomLoader(new URL[] {}, getClass().getClassLoader());
        this.currentLanguage = new LangEN();
        this.availableLangs = new HashMap<>();
        this.availableLangs.put(currentLanguage.getLanguageName(), currentLanguage);
    }

    public void start() throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        List<JarFile> jars = getJars();
        System.out.println(jars.get(0).getName());
        loadLanguage(jars.get(0));
    }

    private List<JarFile> getJars() throws IOException, InstantiationException, IllegalAccessException {
        Collection<File> files = FileUtils.listFiles(new File(AppConst.LANG_DIR), new String[] { AppConst.FILE_EXT },
                true);

        files.stream().forEach(f -> System.out.println(f.getName()));
        List<JarFile> jarFiles = files.stream().map(file -> {
            try {
                return new JarFile(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return jarFiles;
    }

    private boolean loadLanguage(JarFile jarFile)
            throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {

        boolean isLang = false;

        Enumeration<JarEntry> entries = jarFile.entries();
        classLoader.addURL(getJarUrl(jarFile));

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String className = entry.getName().replaceAll("/", ".");

            if (className.endsWith(".class")) {
                System.out.println(className);
                Class<?> clazz = classLoader.loadClass(className.substring(0, className.length() - 6));
                Class<?> superClass = clazz.getSuperclass();
                if (superClass.isAssignableFrom(Language.class)) {
                    Language lang = (Language) clazz.newInstance();
                    availableLangs.put(lang.getLanguageName(), lang);
                    System.out.println(lang.getLanguageName());
                    isLang = true;
                    break;
                }
            }
        }

        System.out.println(isLang);
        return isLang;
    }

    private URL getJarUrl(JarFile jarFile) throws MalformedURLException {
        return new URL("jar:file:" + jarFile.getName() + "!/");
    }

}
