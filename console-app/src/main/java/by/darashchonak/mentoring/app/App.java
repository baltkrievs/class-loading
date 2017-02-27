package by.darashchonak.mentoring.app;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import by.darashchonak.mentoring.app.constant.AppConst;
import by.darashchonak.mentoring.lang.LangEN;
import by.darashchonak.mentoring.langs.Language;
import by.darashchonak.mentoring.loader.CustomLoader;

public class App {

    final static Logger logger = Logger.getLogger(App.class);

    private CustomLoader classLoader;
    // We need at least one default language at startup
    private Language currentLanguage;
    private Map<String, Language> availableLangs;
    Scanner scanner;

    public App() throws IllegalAccessException, IOException {
        this.classLoader = new CustomLoader(new URL[] {}, getClass().getClassLoader());
        this.currentLanguage = new LangEN();
        this.availableLangs = new HashMap<>();
        this.availableLangs.put(currentLanguage.getLocaleName(), currentLanguage);
        this.scanner = new Scanner(System.in);
    }

    public void start() throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {

        String input = "";

        do {
            showMainMenu();
            input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
            case "q":
                scanner.close();
                break;
            case "a":
                loadLanguage(getJars());
                break;
            case "s":
                switchLanguage();
            default:
            }

        } while (!input.equals("q"));
    }

    private List<JarFile> getJars() throws IOException, InstantiationException, IllegalAccessException {
        Collection<File> files = FileUtils.listFiles(new File(AppConst.LANG_DIR), new String[] { AppConst.FILE_EXT },
                true);

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

    private boolean loadLanguage(List<JarFile> jarFiles)
            throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {

        boolean isLang = false;
        List<String> newLangs = new ArrayList<>();

        for (JarFile jarFile : jarFiles) {

            Enumeration<JarEntry> entries = jarFile.entries();
            classLoader.addURL(getJarUrl(jarFile));

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String className = entry.getName().replaceAll("/", ".");

                if (className.endsWith(".class")) {
                    Class<?> clazz = classLoader.loadClass(className.substring(0, className.length() - 6));
                    Class<?> superClass = clazz.getSuperclass();
                    if (superClass.isAssignableFrom(Language.class)) {
                        Language lang = (Language) clazz.newInstance();
                        availableLangs.put(lang.getLocaleName(), lang);
                        newLangs.add(lang.getLanguageName());
                        isLang = true;
                        break;
                    }
                }
            }
        }
        showAddLanguagesDialog(newLangs);
        return isLang;
    }

    private URL getJarUrl(JarFile jarFile) throws MalformedURLException {
        return new URL("jar:file:" + jarFile.getName() + "!/");
    }

    private void showMainMenu() {
        logger.info(currentLanguage.getProperty("lang.ui.text.option.a"));
        logger.info(currentLanguage.getProperty("lang.ui.text.option.s"));
        logger.info(currentLanguage.getProperty("lang.ui.text.option.q"));
    }

    private void showAddLanguagesDialog(List<String> newLangs) {

        if (newLangs.isEmpty()) {
            logger.info(currentLanguage.getProperty("lang.ui.error.not.found"));
        } else {
            logger.info(currentLanguage.getProperty("lang.ui.text.added.langs") + String.join(", ", newLangs));
        }
    }

    private void switchLanguage() {

        String in = "";

        do {
            logger.info(currentLanguage.getProperty("lang.ui.text.available.langs")
                    + String.join(", ", availableLangs.keySet()));
            logger.info(currentLanguage.getProperty("lang.ui.text.option.p"));

            in = scanner.nextLine().trim().toLowerCase();

            switch (in) {
            case "p":
                break;
            default:
                Language lang = availableLangs.get(in);
                if (null == lang) {
                    logger.info(currentLanguage.getProperty("lang.ui.error.bad.input"));
                } else {
                    currentLanguage = lang;
                    logger.info(currentLanguage.getProperty("lang.ui.text.lang.now") + lang.getLanguageName());
                }
            }
        } while (!in.equals("p"));
    }
}
