import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SenderClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8284);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream
                ()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the message: ");
            String message = scanner.nextLine();
            message += message.endsWith("\n") ? "" : "\n";
            writer.write(message);
            writer.flush();
            String input = reader.readLine();
            System.out.println("Received a new message:\n" + input);
        }
    }
}
