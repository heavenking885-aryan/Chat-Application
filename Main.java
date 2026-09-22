public class Main {
    public static void main(String[] args) {
        // Entry point to launch either the server or client based on user choice
        System.out.println("Choose mode: [1] Server  [2] Client");
        int choice = InputValidator.readInt();

        if (choice == 1) {
            new ChatServer().start();
        } else if (choice == 2) {
            new ChatClient().start();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}