package example;

import com.sun.jdi.JDIPermission;
import com.sun.security.jgss.InquireSecContextPermission;
import com.sun.tools.attach.AttachPermission;
import jdk.jfr.FlightRecorderPermission;
import jdk.net.NetworkPermission;

import javax.management.MBeanPermission;
import javax.management.MBeanServerPermission;
import javax.management.MBeanTrustPermission;
import javax.management.remote.SubjectDelegationPermission;
import javax.net.ssl.SSLPermission;
import javax.security.auth.AuthPermission;
import javax.security.auth.kerberos.ServicePermission;
import javax.smartcardio.CardPermission;
import javax.sound.sampled.AudioPermission;
import java.awt.*;
import java.io.DataInputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.SerializablePermission;
import java.lang.management.ManagementPermission;
import java.lang.reflect.ReflectPermission;
import java.net.NetPermission;
import java.net.SocketPermission;
import java.net.URLPermission;
import java.nio.file.LinkPermission;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.security.SecurityPermission;
import java.security.cert.Certificate;
import java.sql.SQLPermission;
import java.util.PropertyPermission;
import java.util.logging.LoggingPermission;

public class Log4JClassLoader extends SecureClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("example") || name.startsWith("org.apache.logging.log4j")) {
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
        codeSource = new CodeSource(null, (Certificate[]) null);
        Permissions permissions = new Permissions();


        permissions.add(new AWTPermission("*"));
//         permissions.add(new AllPermission());
        permissions.add(new AudioPermission("*"));
        permissions.add(new AttachPermission("attachVirtualMachine"));
        permissions.add(new AuthPermission("*"));
        permissions.add(new InquireSecContextPermission("*"));
        permissions.add(new JDIPermission("virtualMachineManager"));
//         permissions.add(new BasicPermission());
        permissions.add(new CardPermission("*", "*"));
        permissions.add(new FilePermission("<<ALL FILES>>", "read,write,delete,execute,readlink"));
        permissions.add(new FlightRecorderPermission("accessFlightRecorder"));
        permissions.add(new InquireSecContextPermission("*"));
        permissions.add(new LinkPermission("symbolic"));
        permissions.add(new LinkPermission("hard"));
        permissions.add(new LoggingPermission("control", ""));
        permissions.add(new MBeanPermission("*", "*"));
        permissions.add(new MBeanServerPermission("*")); // important
        permissions.add(new MBeanTrustPermission("*"));
        permissions.add(new ManagementPermission("monitor"));
        permissions.add(new ManagementPermission("control"));
        permissions.add(new NetPermission("*"));
        permissions.add(new NetworkPermission("*"));
//         permissions.add(new PrivateCredentialPermission("*", "read"));
        permissions.add(new PropertyPermission("*", "read,write"));
        permissions.add(new ReflectPermission("*"));
        permissions.add(new RuntimePermission("*"));
        permissions.add(new SQLPermission("*"));
        permissions.add(new SSLPermission("*"));
        permissions.add(new SecurityPermission("*"));
        permissions.add(new SerializablePermission("*"));
        permissions.add(new ServicePermission("*", "-"));
        permissions.add(new SocketPermission("", "connect,listen,accept"));
        permissions.add(new SubjectDelegationPermission("*"));
        permissions.add(new URLPermission("http://0.0.0.0"));
//         permissions.add(new UnresolvedPermission("*", "*")); // don't know


        return new ProtectionDomain(codeSource, permissions);
    }
}
