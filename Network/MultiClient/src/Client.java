import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8284);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream
                ()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(() -> {
            while (true) {
                try {
                    String message = reader.readLine();
                    System.out.println("A client: " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("You: ");
                    String message = scanner.nextLine();
                    message += message.endsWith("\n") ? "" : "\n";
                    writer.write(message);
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
