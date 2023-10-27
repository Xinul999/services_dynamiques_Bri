package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientAma {

    private final static String HOST = "localhost";
    private static final int PORT_AMA = 3000;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT_AMA);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Services disponibles :");
            out.println("GET_SERVICES");
            String servicesList = in.readLine();
            System.out.println(servicesList);

            System.out.print("Choisissez un service : ");
            String chosenService = userInput.readLine();

            out.println("CHOICE " + chosenService);

            String response = in.readLine();
            System.out.println("Réponse du serveur : " + response);

            out.close();
            in.close();
            userInput.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
