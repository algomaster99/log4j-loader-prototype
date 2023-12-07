package example;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // use custom classloader to load log4j classes
        ClassLoader customClassLoader = new Log4JClassLoader();
        Class<?> clazz = customClassLoader.loadClass("example.ReflectiveLogging");

        clazz.getMethod("reflectiveLogging", String.class).invoke(null, "Hello, world!");

    }
}
