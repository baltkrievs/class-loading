package by.darashchonak.mentoring.app;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import by.darashchonak.mentoring.app.constant.AppConst;

public class Main {

    public static void main(String[] args)
            throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {

        Path langDir = FileSystems.getDefault().getPath(AppConst.LANG_DIR);
        if (Files.notExists(langDir)) {
            Files.createDirectories(langDir);
        } else {
            if (!Files.isDirectory(langDir)) {
                throw new IOException("Could not make " + langDir + " directory");
            }
        }

        App application = new App();
        application.start();

    }

}
