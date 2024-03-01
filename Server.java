import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening... Port: " + port);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        //Accept and handle connections
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("Connection accepted.");

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                String line = null;
                line = input.readLine();
                System.out.println("Request: " + line);

                input.close();
                output.close();
                socket.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
}
