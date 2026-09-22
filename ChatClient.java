import java.io.*;
import java.net.*;

public class ChatClient {
    public void start() {
        try (Socket socket = new Socket("localhost", 12345);


             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            new Thread(() -> {
                String serverMsg;
                try {
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            }).start();

            String userInput;
            while ((userInput = reader.readLine()) != null) {
                out.println(userInput);
                if (userInput.equalsIgnoreCase("exit")) break;
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}