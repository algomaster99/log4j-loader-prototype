package example;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureClassLoader;

public class Log4JClassLoader extends SecureClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("example") || name.startsWith("org.apache.logging.log4j.")) {
            System.out.println("Loading class from " + Log4JClassLoader.class + name);
            String classfile = name.replace('.', '/') + ".class";

            InputStream is = getResourceAsStream(classfile);
            byte[] bytes;
            try {
                bytes = new byte[is.available()];
                DataInputStream dis = new DataInputStream(is);
                dis.readFully(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Class<?> customLoadedClass = defineClass(name, bytes, 0, bytes.length);
            resolveClass(customLoadedClass);
            return customLoadedClass;
        }
        return super.loadClass(name);
    }
}
