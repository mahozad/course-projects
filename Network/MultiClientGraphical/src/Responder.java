import java.util.*;
import java.net.*;
import java.io.*;

class Responder implements Runnable {

    private static final List<Responder> responders = Collections.synchronizedList(new
            LinkedList<Responder>());
    private BufferedReader reader;
    private BufferedWriter writer;

    Responder(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        synchronized (responders) {
            responders.add(this);
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while (true) {
                message = reader.readLine();
                if (message.length() != 0) {
                    System.out.println("new Message: \"" + message + "\"");
                }
                message += message.endsWith("\n") ? "" : "\n";
                sendMessage(this, message);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
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

    private void writeMessage(String message) throws IOException {
        writer.write(message);
        writer.flush();
    }
}
