package appli;

import bri.ServeurBRi;
import bri.Service;
import bri.ServiceRegistry;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import static java.net.URLClassLoader.newInstance;

public class BRiLaunch {
	private final static int PORT_AMA = 3000;
	private final static int PORT_PROG = 4000;

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);

		String linkFtp = "ftp://localhost:21/";
		//String linkFtp = "ftp://localhost:21/res/home/classes/";
		URLClassLoader urlcl = newInstance(new URL[]{new URL(linkFtp)});

		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
		System.out.println("Pour ajouter une activité, celle-ci doit être présente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'intégrer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activité (ClientAma) et au serveur 4000 pour les amateurs (ClientProg)");

		new Thread(new ServeurBRi(PORT_PROG)).start();
		new Thread(new ServeurBRi(PORT_AMA)).start();

		while (true) {
			try {
				String classeName = clavier.next();
				Class<? extends Service> classCharge = urlcl.loadClass(classeName).asSubclass(Service.class);
				ServiceRegistry.addService(classCharge);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static void testFTP() {
	}
}
