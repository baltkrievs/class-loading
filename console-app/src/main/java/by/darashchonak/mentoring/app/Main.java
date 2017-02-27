package by.darashchonak.mentoring.app;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
            throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {

        App application = new App();
        application.start();

        String input = "";

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Alaivable languages: ");
            System.out.println("to add language enter a");

            input = scanner.nextLine();
        }

        while (!input.trim().toLowerCase().equals("q"));
    }

}
