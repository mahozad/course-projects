import java.io.*;
import java.nio.file.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT_NUMBER = 8248;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER); Socket textSocket =
                serverSocket.accept(); Socket binarySocket = serverSocket.accept();
                BufferedReader textReader = new BufferedReader(new InputStreamReader(textSocket
                        .getInputStream())); BufferedWriter textWriter = new BufferedWriter(new
                OutputStreamWriter(textSocket.getOutputStream())); BufferedOutputStream
                     binaryWriter = new BufferedOutputStream(binarySocket.getOutputStream())) {

            System.out.println("Server started!");
            Path path = Paths.get(textReader.readLine());
            if (!Files.exists(path)) {
                textWriter.write("File not found!\n");
                textWriter.flush();
            } else if (!Files.isRegularFile(path)) {
                textWriter.write("Not a regular file!\n");
                textWriter.flush();
            } else if (!Files.isReadable(path)) {
                textWriter.write("File cannot be read from!\n");
                textWriter.flush();
            } else {
                textWriter.write("File successfully opened!\n");
                textWriter.flush();

                double fileSize = Files.size(path);
                long bytesCopied = 0;
                try (InputStream fileInputStream = new FileInputStream(path.toString())) {
                    int data;
                    while ((data = fileInputStream.read()) != -1) {
                        binaryWriter.write(data);
                        bytesCopied++;
                        System.out.printf(bytesCopied < 10 ? "" : "\r" + "\r\r%.0f%%",
                                (bytesCopied / fileSize) * 100);
                    }
                    binaryWriter.flush();
                }
                textWriter.write("File copied successfully!");
                textWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
