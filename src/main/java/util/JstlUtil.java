package util;

import entity.db.Permission;

import java.util.List;

public class JstlUtil {

    public static boolean containsPermission(List<Permission> permissions, String permissionName){
        for(Permission permission: permissions){
            if(permission.getName().equals(permissionName)){
                return true;
            }
        }
        return false;
    }

}

