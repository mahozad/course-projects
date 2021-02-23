import java.awt.*;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {
        SocialMain program = new SocialMain();
        try {
            program.initialSocial();
        } catch (IOException | NoSuchElementException | HeadlessException | NullPointerException
                ignored) {
        }
    }
}