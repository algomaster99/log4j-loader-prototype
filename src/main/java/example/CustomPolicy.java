package example;

import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;

public class CustomPolicy extends Policy {

    @Override
    public PermissionCollection getPermissions(ProtectionDomain domain) {
        PermissionCollection permissions = new Permissions();
        return permissions;
    }

}
