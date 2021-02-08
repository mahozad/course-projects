import java.io.*;
import java.net.Socket;

public class Client {

    private static final String SRC_PATH = "C:\\Users\\Mahdi\\Desktop\\logo.png";
    private static final File DEST_FILE = new File("C:\\Users\\Mahdi\\Desktop\\cop.png");
    private static final String HOST = "localhost";
    private static final int PORT_NUMBER = 8248;

    public static void main(String[] args) {

        try (Socket textSocket = new Socket(HOST, PORT_NUMBER); Socket binarySocket = new Socket
                (HOST, PORT_NUMBER); FileOutputStream fileOutputStream = new FileOutputStream
                (DEST_FILE); BufferedReader textReader = new BufferedReader(new InputStreamReader
                (textSocket.getInputStream())); BufferedWriter textWriter = new BufferedWriter
                (new OutputStreamWriter(textSocket.getOutputStream())); BufferedInputStream
                binaryReader = new BufferedInputStream(binarySocket.getInputStream())) {

            textWriter.write(SRC_PATH + "\n");
            textWriter.flush();

            System.out.println("Server: " + textReader.readLine());

            int data;
            while ((data = binaryReader.read()) != -1) {
                fileOutputStream.write(data);
            }
            System.out.println(textReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
