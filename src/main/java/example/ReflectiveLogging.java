package example;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class ReflectiveLogging {
    public static void reflectiveLogging(String message) {
        LogManager.getLogger().log(Level.ERROR, message);
    }
}
