package entity.client;

import entity.db.Permission;

import java.util.ArrayList;
import java.util.List;

public class RoleData {

    private int idRole;
    private String name;
    private String description;
    private List<Permission> permissions;
    private List<Permission> missingPermissions;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getMissingPermissions() {
        return missingPermissions;
    }

    public void setMissingPermissions(List<Permission> missingPermission) {
        this.missingPermissions = missingPermission;
    }

}

