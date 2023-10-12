package Utile;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class Tools {
    private static StringBuilder check = new StringBuilder();
    public static void implement(Class<?> clazz, String name) {
        boolean ret = Arrays.stream(clazz.getInterfaces())
                .anyMatch(i -> i.getName().toLowerCase().contains(name.toLowerCase()));
        if (!ret) {
            check.append(clazz.getName()).append(" ne possède pas l' interface ").append(name).append("\n");
        }

    }
    public static void modifier(Class<?> clazz){
        if(!Modifier.isPublic(clazz.getModifiers())){
            check.append(clazz.getName()).append(" n'est pas public\n");
        }
        if(Modifier.isAbstract(clazz.getModifiers())){
            check.append(clazz.getName()).append(" ne doit pas être abstract\n");
        }
    }

    public static void constructor(Class<?> clazz, String parameter){
        Constructor[] constructors = clazz.getConstructors();
        if(constructors.length == 1){
            Class<?>[] params = constructors[0].getParameterTypes();
            boolean t1 = params[0].getName().toLowerCase().contains(parameter.toLowerCase());
            if(!t1){
                check.append(clazz.getName()).append(" ne doit avoir que ").append(parameter);
            }
        }else {
            check.append(clazz.getName()).append(" doit avoir un paramètre \n");
        }
    }
    public static void constructorWithoutException(Class<?> clazz){
       List<?> noError = Arrays.stream(clazz.getConstructors()).filter(c -> c.getExceptionTypes().length > 0).toList();
       if(!noError.isEmpty()){
           check.append(clazz.getName()).append(" ne doit pas avoir une exception\n");
       }

    }

    public static void attributSocket(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        // Socket private final
        boolean isOk = false;
        for (Field field : fields) {
           if(Modifier.isPrivate(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.getName().toLowerCase().contains("socket")){
              isOk = true;
              break;
           }
        }

        if(!isOk){
            check.append(clazz.getName()).append(" doit possèder private final Socket\n");
        }
    }

    public static String getError(){
        return check.toString();
    }
    public static void clearError(){
        check.delete(0, check.length());
    }
}
