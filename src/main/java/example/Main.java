package example;

import java.lang.reflect.InvocationTargetException;
import java.security.Policy;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // use custom classloader to load log4j classes
        Policy.setPolicy(new CustomPolicy());
        System.setSecurityManager(new SecurityManager());

        Log4JClassLoader customClassLoader = new Log4JClassLoader();
        Class<?> clazz = customClassLoader.loadClass("example.ReflectiveLogging");

        clazz.getMethod("reflectiveLogging", String.class).invoke(null, "Hello, world!");

    }
}
