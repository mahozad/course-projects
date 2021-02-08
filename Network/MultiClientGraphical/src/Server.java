import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8284);
            System.out.println("\n\t\t\t.:: Server started ::.\n");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected!");
                Responder responder = new Responder(socket);
                new Thread(responder).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
