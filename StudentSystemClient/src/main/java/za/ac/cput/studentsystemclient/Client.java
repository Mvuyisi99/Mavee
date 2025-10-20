package za.ac.cput.studentsystemclient;

import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static String sendToServer(String message) {
        String response = "";
        try (Socket socket = new Socket(HOST, PORT)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(message);
            
            response = reader.readLine();
        } catch (IOException e) {
            response = "ERROR:Client exception: " + e.getMessage();
        }
        return response;
    }
}
