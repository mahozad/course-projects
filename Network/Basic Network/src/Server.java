import java.io.*;
import java.net.*;

public class Server {

    private static Socket socket;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8428); // the welcoming door
        while (true) {
            socket = serverSocket.accept(); // dedicate a socket for the knocking client
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket
                            .getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket
                            .getOutputStream()));
                    String input = reader.readLine();
                    System.out.println("Received String \"" + input + "\" from a client");
                    writer.write(input.toUpperCase());
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
