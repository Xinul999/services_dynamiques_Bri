package appli;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurBRi;
import bri.Service;
import bri.ServiceRegistry;

import static java.net.URLClassLoader.newInstance;

public class BRiLaunch {
	private final static int PORT_SERVICE = 3000;
	
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		
		// URLClassLoader sur ftp
		String linkFtp = "ftp://localhost:21/";
		//String linkFtp = "ftp://localhost:21/res/home/classes/";
		URLClassLoader urlcl = newInstance(new URL[]{new URL(linkFtp)});
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activit? BRi");
		System.out.println("Pour ajouter une activit?, celle-ci doit ?tre pr?sente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'int?grer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activit?");
		
		new Thread(new ServeurBRi(PORT_SERVICE)).start();
		
		while (true){
				try {
					String classeName = clavier.next();
					// charger la classe et la déclarer au ServiceRegistry
					// examples.ServiceInversion
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
