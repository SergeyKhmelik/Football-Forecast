package entity.client;

import entity.db.Permission;
import entity.db.Role;
import entity.db.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

    private int idUser;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Role role;
    private List<Permission> permissions;

    public UserInfo(){}

    public UserInfo(User user) {
        this.idUser = user.getIdUser();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
