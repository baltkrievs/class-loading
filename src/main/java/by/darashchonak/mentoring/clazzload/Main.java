package by.darashchonak.mentoring.clazzload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {

        // // Setup terminal and screen layers
        // Terminal terminal = new DefaultTerminalFactory().createTerminal();
        // Screen screen = new TerminalScreen(terminal);
        // screen.startScreen();
        //
        // // Create panel to hold components
        // Panel panel = new Panel();
        // panel.setLayoutManager(new GridLayout(2));
        //
        // panel.addComponent(new Label("Forename"));
        // panel.addComponent(new TextBox());
        //
        // panel.addComponent(new Label("Surname"));
        // panel.addComponent(new TextBox());
        //
        // panel.addComponent(new EmptySpace(new TerminalSize(0, 0))); // Empty
        // // space
        // // underneath
        // // labels
        // panel.addComponent(new Button("Submit"));

        // Create window to hold the panel
        // BasicWindow window = new BasicWindow();
        // window.setComponent(panel);

        // Create gui and start gui
        // MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new
        // DefaultWindowManager(),
        // new EmptySpace(TextColor.ANSI.BLUE));
        // gui.addWindowAndWait(window);

        InputStream input;// = new FileInputStream("lang.properties");
        input = Main.class.getClassLoader().getResourceAsStream("lang.properties");
        Properties props = new Properties();
        props.load(input);

        String text = props.getProperty("lang.ui.text");

        System.out.println(text);

    }
}
