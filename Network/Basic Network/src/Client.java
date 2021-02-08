import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Socket socket = new Socket("localhost", 8428); BufferedReader bufferedReader = new
                BufferedReader(new InputStreamReader(socket.getInputStream())); BufferedWriter
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream
                ()))) {
            System.out.println("Enter your string: ");
            String nextString = scanner.nextLine();
            bufferedWriter.write(String.format("%s\n", nextString));
            bufferedWriter.flush();
            String response = bufferedReader.readLine();
            System.out.println("Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
