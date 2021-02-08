import java.util.*;
import java.net.*;
import java.io.*;

class Responder implements Runnable {

    private static List<Responder> responders;
    private BufferedReader reader;
    private BufferedWriter writer;

    static void setResponders(List<Responder> listOfResponders) {
        responders = listOfResponders;
    }

    Responder(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            String message;
            while (true) {
                message = reader.readLine();
                System.out.println("new Message: " + message);
                message += message.endsWith("\n") ? "" : "\n";
                sendMessage(this, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeMessage(String message) throws IOException {
        writer.write(message);
        writer.flush();
    }

    private static synchronized void sendMessage(Responder sender, String message) throws
            IOException {
        synchronized (responders) {
            for (Responder responder : responders) {
                if (!responder.equals(sender)) {
                    responder.writeMessage(message);
                }
            }
        }
    }
}
