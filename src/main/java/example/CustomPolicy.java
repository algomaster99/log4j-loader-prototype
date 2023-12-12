package example;

import java.security.AllPermission;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;

public class CustomPolicy extends Policy {

    @Override
    public PermissionCollection getPermissions(ProtectionDomain domain) {
        PermissionCollection permissions = new Permissions();
        // so that custom classloader can be created and then it can load log4j classes
        permissions.add(new AllPermission());
        return permissions;
    }

    @Override
    public boolean implies(ProtectionDomain domain, Permission permission) {
        return getPermissions(domain).implies(permission);
    }

}
