import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        ServerSocket serverSocket = null;
        //Set up the server
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening... Port: " + port);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        //Set up a database
        Database database = new Database();

        //Accept and handle connections
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("Connection accepted.");

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                //Mode: 1 = register, 2 = auth
                int mode = 0;
                String line = input.readLine();
                for (int i = 0; line != null; i++) {
                    if (i == 0 && line.contains("register")) {
                        System.out.println("DEBUG: register");
                        mode = 1;
                    } else if (i == 0) {
                        System.out.println("DEBUG: auth");
                        mode = 2;
                    }

                    if (i == 8 && mode == 1) {
                        System.out.println("DEBUG: Register mode");

                        //Extract username
                        String username = "";
                        Pattern pattern = Pattern.compile("\"username\":\"([^\"]*)\"");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            username = matcher.group(1);
                            mode = 0;
                        } else {
                            System.out.println("Error: username not found");
                            mode = 0;
                        }

                        //Extract email
                        String email = "";
                        pattern = Pattern.compile("\"email\":\"([^\"]*)\"");
                        matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            email = matcher.group(1);
                            mode = 0;
                        } else {
                            System.out.println("Error: username not found");
                            mode = 0;
                        }

                        //Store username and email in database
                        if (!(username.equals("") || email.equals(""))){
                            System.out.println("Storing username and email: " + username + " " + email);
                            database.AddEntry(username, email);
                        } else {
                            System.out.println("Error: " + username + " " + email);
                        }

                        //Give HTTP response
                        output.println("HTTP/1.1 201 Created");

                        //Headers
                        output.println("Content-Type: application/json");
                        output.println("Charset: ASCII");

                        //End of headers
                        output.println();

                        //Body
                        output.println("{");
                        output.println("  \"message\": \"User registered successfully\",");
                        output.println("  \"email\": \"" + email +"\",");
                        output.println("  \"username\": \"" + username + "\"");
                        output.println("}");

                        output.flush(); //Ensure the response is sent
                    } else if (i == 8 && mode == 2) {
                        System.out.println("DEBUG: Auth mode");

                    }
                    System.out.println("Request: " + line);
                    line = input.readLine();
                }


                input.close();
                output.close();
                socket.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
}
