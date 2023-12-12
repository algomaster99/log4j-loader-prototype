package example;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;


public class LDAPServer {
    private static final String LDAP_BASE = "dc=example";

    public static void main(String[] args) throws LDAPException, IOException {
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
        config.setListenerConfigs(new InMemoryListenerConfig("listen", InetAddress.getByName("0.0.0.0"), 8000, ServerSocketFactory.getDefault(), SocketFactory.getDefault(), (SSLSocketFactory) SSLSocketFactory.getDefault()));

        InMemoryDirectoryServer directoryServer = new InMemoryDirectoryServer(config);

        // TODO: try to add a java class file to the LDAP server
//        Entry javaClassFile =  new Entry(LDAP_BASE);
//        javaClassFile.addAttribute("javaClassName", "MaliciousClass");
//        directoryServer.getSchema();
//        javaClassFile.addAttribute("javaCodeBase", Files.readAllBytes(Paths.get("src/main/resources/MaliciousClass.class")));
//        javaClassFile.addAttribute("objectClass", "ldapSubEntry");
//        directoryServer.add(javaClassFile);
        directoryServer.startListening();

    }
}
