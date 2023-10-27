package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProg {

    private final static String HOST = "localhost";
    private final static int PORT_PROG = 4000;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT_PROG);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Veuillez vous authentifier :");
            System.out.print("Login : ");
            String login = userInput.readLine();
            System.out.print("Mot de passe : ");
            String password = userInput.readLine();

            out.println("AUTH " + login + " " + password);

            String response = in.readLine();
            System.out.println("Réponse du serveur : " + response);

            if (response.equals("AUTH_SUCCESS")) {
                System.out.println("Authentification réussie. Choisissez une action :");
                System.out.println("1. Fournir un nouveau service");
                System.out.println("2. Mettre à jour un service");
                System.out.println("3. Déclarer un changement d'adresse FTP");

                int choice = Integer.parseInt(userInput.readLine());

                out.println("CHOICE " + choice);
                String actionResponse = in.readLine();
                System.out.println("Réponse du serveur : " + actionResponse);
            } else {
                System.out.println("Authentification échouée. Veuillez vérifier vos informations.");
            }

            out.close();
            in.close();
            userInput.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
