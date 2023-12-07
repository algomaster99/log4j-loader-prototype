package example;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReflectiveLogging {
    public static void reflectiveLogging(String message) throws IOException {
        LogManager.getLogger().log(Level.ERROR, message);
        String content = Files.readAllLines(Paths.get("a.txt")).get(0);
        System.out.println(content);
    }
}
