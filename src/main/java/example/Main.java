package example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.Policy;

/**
 * Execute {@link LDAPServer} first
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // use custom classloader to load log4j classes
        Policy.setPolicy(new CustomPolicy());
        System.setSecurityManager(new SecurityManager());

        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("http://localhost:8000/")})) {
            Log4JClassLoader customClassLoader = new Log4JClassLoader();
            Class<?> clazz = customClassLoader.loadClass("example.ReflectiveLogging");

            clazz.getMethod("reflectiveLogging", String.class).invoke(null, "Hello, world!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
