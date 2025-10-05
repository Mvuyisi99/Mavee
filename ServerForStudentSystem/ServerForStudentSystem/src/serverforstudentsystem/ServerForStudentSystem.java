/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package serverforstudentsystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Mvuyi
 */
public class ServerForStudentSystem {

    private ServerSocket listener;

    private Socket client;
    String msg;
    ObjectOutputStream out;
    ObjectInputStream in;

     public ServerForStudentSystem() {
        // Create server socket
        try {

            listener = new ServerSocket(6666, 1);

        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }
     public void acceptEnrollMent() {
        // Start listening for client connections
        try {
            System.out.println("Server is listening");
            client = listener.accept();
            System.out.println("Now moving onto processClient");
            out = new ObjectOutputStream(client.getOutputStream());
            out.flush();
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

}
