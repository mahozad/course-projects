import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static final List<Responder> responders = Collections.synchronizedList(new
            LinkedList<Responder>());

    public static void main(String[] args) {
        try {
            Responder.setResponders(responders);
            ServerSocket serverSocket = new ServerSocket(8284);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client connected!");
                Responder responder = new Responder(socket);
                responders.add(responder);
                new Thread(responder).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
