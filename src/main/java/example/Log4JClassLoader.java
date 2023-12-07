package example;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.security.cert.Certificate;

public class Log4JClassLoader extends SecureClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("example") || name.startsWith("org.apache.logging.log4j.")) {
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

            Class<?> customLoadedClass = defineClass(name, bytes, 0, bytes.length, getProtectionDomain());
            resolveClass(customLoadedClass);
            return customLoadedClass;
        }
        return super.loadClass(name);
    }

    private static ProtectionDomain getProtectionDomain() {
        CodeSource codeSource;
        try {
            codeSource = new CodeSource(new URL("file://tmp/untitled1/target/classes/example/ReflectiveLogging.class"), (Certificate[]) null);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Permissions permissions = new Permissions();
        permissions.setReadOnly();
        return new ProtectionDomain(codeSource, permissions);
      }
}
