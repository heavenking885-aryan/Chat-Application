import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private final Socket socket;
    private final ChatServer server;
    private PrintWriter out;
    private String userName;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter writer = new PrintWriter(
                socket.getOutputStream(), true
            )
        ) {
            this.out = writer;

            writer.println("Enter your name:");
            userName = in.readLine();

            if (userName == null || userName.trim().isEmpty()) {
                return;
            }

            server.broadcast(userName + " has joined the chat.", this);

            String clientMessage;

            while ((clientMessage = in.readLine()) != null) {
                if (clientMessage.equalsIgnoreCase("exit")) {
                    break;
                }

                if (!clientMessage.trim().isEmpty()) {
                    server.broadcast(
                        userName + ": " + clientMessage,
                        this
                    );
                }
            }

            server.broadcast(userName + " has left the chat.", this);

        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // Socket is already closed or could not be closed.
            }
        }
    }
}