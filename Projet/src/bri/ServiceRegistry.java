package bri;

import Utile.Tools;

import java.lang.reflect.Method;
import java.util.List;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partagée en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = null;
	}
	private static List<Class<? extends Service>> servicesClasses;

// ajoute une classe de service apr?s contr?le de la norme BLTi
	public static void addService(Class<? extends Service> service) {
		// vérifier la conformité par introspection
		// si non conforme --> exception avec message clair
		// si conforme, ajout au vector
		Tools.implement(service, "BRi.Service");
		Tools.modifier(service);
		Tools.constructor(service, "Socket");
		Tools.constructorWithoutException(service);
		Tools.attributSocket(service);
	}
	
// renvoie la classe de service (numService -1)	
	public static Class<?> getServiceClass(int numService) {
		if(0 <= numService && numService < servicesClasses.size()) {
			return servicesClasses.get(numService);
		}
		return null;
	}
	
// liste les activit?s pr?sentes
	public static String toStringue() throws NoSuchMethodException {
		StringBuilder result = new StringBuilder("Activités présentes :##");
		synchronized (servicesClasses) {
			for (int i = 0; i < servicesClasses.size(); i++) {
				try {
					Method m = servicesClasses.get(i).getMethod("toStringue");
					m.invoke(servicesClasses.get(i));
				}catch(Exception e) {
					e.printStackTrace();
				}
				result.append("N ° activité : ").append(i).append(1).append(" nom service :").append(servicesClasses.get(i).getName());
			}
		}
		return result.toString();
	}

}
